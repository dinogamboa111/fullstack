package cl.fullstack.pedido_ms.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import cl.fullstack.pedido_ms.client.CentroDistribucionClient;
import cl.fullstack.pedido_ms.client.ClienteClient;
import cl.fullstack.pedido_ms.client.ProductoClient;
import cl.fullstack.pedido_ms.client.UbicacionClient;
import cl.fullstack.pedido_ms.client.UsuarioClient;
import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.dto.PedidoDTO;
import cl.fullstack.pedido_ms.dto.external.CentroDistribucionDTO;
import cl.fullstack.pedido_ms.dto.external.ClienteDTO;
import cl.fullstack.pedido_ms.dto.external.ComunaDTO;
import cl.fullstack.pedido_ms.dto.external.ProductoDTO;
import cl.fullstack.pedido_ms.dto.external.UsuarioDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoEntity;
import cl.fullstack.pedido_ms.entity.PedidoEntity;
import cl.fullstack.pedido_ms.exception.DatosInvalidosException;
import cl.fullstack.pedido_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pedido_ms.repository.DetallePedidoRepository;
import cl.fullstack.pedido_ms.repository.PedidoRepository;
import cl.fullstack.pedido_ms.service.IPedidoService;

@Service
public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductoClient productoClient;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private UbicacionClient ubicacionClient;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private CentroDistribucionClient centroDistribucionClient;

    @Override
    public PedidoDTO createPedido(PedidoDTO dto) {
        if (dto == null) {
            throw new DatosInvalidosException("Los datos del pedido no pueden ser nulos");
        }

        if (dto.getRutCliente() <= 0) {
            throw new DatosInvalidosException("Debe proporcionar un ID de cliente válido");
        }

        ClienteDTO cliente = clienteClient.obtenerClienteByRut(dto.getRutCliente());
        if (cliente == null) {
            throw new RecursoNoEncontradoException("Cliente con ID " + dto.getRutCliente() + " no existe");
        }

        if (dto.getIdComuna() <= 0) {
            throw new DatosInvalidosException("Debe proporcionar un ID de comuna válido.");
        }

        ComunaDTO comuna = ubicacionClient.obtenerComunasById(dto.getIdComuna());
        if (comuna == null) {
            throw new RecursoNoEncontradoException("Comuna con ID " + dto.getIdComuna() + " no existe");
        }

        for (DetallePedidoDTO detalleDTO : dto.getDetallePedido()) {
            ProductoDTO producto = productoClient.obtenerProductoPorId(detalleDTO.getProductoId());
            if (producto == null) {
                throw new RecursoNoEncontradoException("Producto con ID " + detalleDTO.getProductoId() + " no existe.");
            }
        }

        Optional<CentroDistribucionDTO> centroOpt = centroDistribucionClient.obtenerCentroPorComuna(dto.getIdComuna());

        if (centroOpt.isEmpty() || centroOpt.get().getIdCentro() == null) {
            throw new RecursoNoEncontradoException(
                    "No se encontró centro de distribución para la comuna ID " + dto.getIdComuna());
        }

        dto.setIdCentro(centroOpt.get().getIdCentro());

        // Obtener despachador asignado al centro de distribución
        UsuarioDTO despachador = usuarioClient.obtenerDespachadorPorCentro(dto.getIdCentro());
        if (despachador == null) {
            throw new RecursoNoEncontradoException(
                    "No se encontró un despachador asignado al centro de distribución ID " + dto.getIdCentro());
        }
        dto.setIdDespachador(despachador.getId());

        PedidoEntity pedido = modelMapper.map(dto, PedidoEntity.class);

        pedido.setDetallePedido(null);
        PedidoEntity pedidoGuardado = pedidoRepository.save(pedido);

        List<DetallePedidoEntity> detalles = dto.getDetallePedido().stream().map(detalleDTO -> {
            DetallePedidoEntity detalle = new DetallePedidoEntity();
            detalle.setPedidoId(pedidoGuardado.getIdPedido());
            detalle.setProductoId(detalleDTO.getProductoId());
            detalle.setCantidad(detalleDTO.getCantidad());
            return detalle;
        }).collect(Collectors.toList());

        detallePedidoRepository.saveAll(detalles);
        pedidoGuardado.setDetallePedido(detalles);

        return modelMapper.map(pedidoGuardado, PedidoDTO.class);
    }

    @Override
    public PedidoDTO getPedidoById(int id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("pedido no encontrado con ID: " + id));
        return modelMapper.map(pedido, PedidoDTO.class);
    }

    @Override
    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO updatePedido(int id, PedidoDTO dto) {
        PedidoEntity pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));

        // Actualizar campos con los datos del DTO
        pedidoExistente.setNumCalle(dto.getNumCalle());
        pedidoExistente.setNombreCalle(dto.getNombreCalle());
        pedidoExistente.setEstadoPedido(dto.isEstadoPedido());
        pedidoExistente.setIdComuna(dto.getIdComuna());
        pedidoExistente.setRutCliente(dto.getRutCliente());
        pedidoExistente.setDvCliente(dto.getDvCliente());
        pedidoExistente.setIdDespachador(dto.getIdDespachador());

        // Si quieres también actualizar detalles, pero normalmente se hace en otro
        // método

        pedidoRepository.save(pedidoExistente);
        return modelMapper.map(pedidoExistente, PedidoDTO.class);
    }

    @Override
    public void deletePedido(int id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));
        pedidoRepository.delete(pedido);
    }
}

// @Override

// public PedidoDTO createPedido(PedidoDTO dto) {
// //primero validamos que la dto no sea nulo
// if(dto==null){
// throw new DatosInvalidosException("Los datos del pedido no pueden ser
// nulos");

// }
// //verificar rut de cliente (aqui tal vez hay q verificar ejemplo largo del
// rut?)
// if(dto.getRutCliente()<=0){
// throw new DatosInvalidosException("Debe proporcionar un ID de cliente
// valido");

// }
// ClienteDTO cliente = clienteClient.obtenerClienteByRut(dto.getRutCliente());
// if(cliente==null) {
// throw new RecursoNoEncontradoException("Cliente con ID" + dto.getRutCliente()
// + "no existe");

// }

// //verificar id comuna
// if(dto.getIdComuna() <=0){
// throw new DatosInvalidosException("Debe proporcionar un ID de comuna
// válido.");

// }
// ComunaDTO comuna= ubicacionClient.obtenerComunasById(dto.getIdComuna());
// if (comuna==null){
// throw new RecursoNoEncontradoException("Comuna con Id"+ dto.getIdComuna() +
// "no existe");
// }

// //verificar idproducto

// // primero recorremos la lista de los detalles del pedido, y por cada
// producto
// // consultamos a producto-ms, lo hacemos al principio porque si uno no
// existe,
// // se lanza la exepcion y no se guarda un pedido sin el detalle, de lo
// contrario
// // aunque el producto no exista, estare guardando el pedido solito en la
// tabla
// // pedido

// for (DetallePedidoDTO detalleDTO : dto.getDetallePedido()) {
// // aqui estamos usando el metodo creado en la clase Productocliente, que se
// // comunica con producto-ms para traer los productos y tenerlos aca
// ProductoDTO producto =
// productoClient.obtenerProductoPorId(detalleDTO.getProductoId());
// if (producto == null) {
// // excepcion si el id del producto ingresado no existe
// throw new RecursoNoEncontradoException("Producto con ID " +
// detalleDTO.getProductoId() + " no existe.");
// }
// }

// // mapeamos el pedido dto a entidad, para poder guardarla en la base de datos
// PedidoEntity pedido = modelMapper.map(dto, PedidoEntity.class);

// // guardamos el pedido solo, null, para que evitar problemas con el cascade
// si
// // JPA intenta guardar los detalles antes de que tengan bien seteado el
// pedidoId.
// pedido.setDetallePedido(null);
// PedidoEntity pedidoGuardado = pedidoRepository.save(pedido);

// // aqui construimos la entidad detallepedido con la pk compuesta pedidoid ya
// se
// // genero, y producto id ya se validaron
// List<DetallePedidoEntity> detalles =
// dto.getDetallePedido().stream().map(detalleDTO -> {
// DetallePedidoEntity detalle = new DetallePedidoEntity();
// detalle.setPedidoId(pedidoGuardado.getIdPedido());
// detalle.setProductoId(detalleDTO.getProductoId());
// detalle.setCantidad(detalleDTO.getCantidad());
// return detalle;
// }).collect(Collectors.toList());

// // guardamos los detalles del pedido
// detallePedidoRepository.saveAll(detalles);

// // asignamos los detalles al pedido solo para q los devuelva la dto por json
// pedidoGuardado.setDetallePedido(detalles);

// // devolvemos el objeto pedido con sus detalles
// return modelMapper.map(pedidoGuardado, PedidoDTO.class);
// }

// @Override
// public PedidoDTO getPedidoById(int id) {
// PedidoEntity pedido = pedidoRepository.findById(id)
// .orElseThrow(() -> new RecursoNoEncontradoException("pedido no encontrado con
// ID: " + id));
// return modelMapper.map(pedido, PedidoDTO.class);
// }

// @Override
// public List<PedidoDTO> getAllPedidos() {
// return pedidoRepository.findAll().stream()
// .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
// .collect(Collectors.toList());
// }

// @Override
// public PedidoDTO updatePedido(int id, PedidoDTO dto) {
// pedidoRepository.findById(id)
// .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrada con
// ID: " + id));
// dto.setRutCliente(id); // aseguramos que no cambie el ID
// PedidoEntity actualizado = modelMapper.map(dto, PedidoEntity.class);
// return modelMapper.map(pedidoRepository.save(actualizado), PedidoDTO.class);
// }

// @Override
// public void deletePedido(int id) {
// PedidoEntity pedido = pedidoRepository.findById(id)
// .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrada con
// ID: " + id));
// pedidoRepository.delete(pedido);
// }

// }
