package cl.fullstack.pedido_ms.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

//aki
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


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
    

    //metodo para crear pedido
    
    @Override
public PedidoDTO createPedido(PedidoDTO pedidoDTO) {
    // 1. Validaciones básicas de datos
    if (pedidoDTO.getRutCliente() <= 0) {
        throw new DatosInvalidosException("El RUT del cliente debe ser mayor que cero.");
    }

    if (pedidoDTO.getDvCliente() == '\0' || Character.isWhitespace(pedidoDTO.getDvCliente())) {
        throw new DatosInvalidosException("El dígito verificador del cliente es obligatorio.");
    }

    if (pedidoDTO.getNumCalle() == null || pedidoDTO.getNumCalle().isBlank()) {
        throw new DatosInvalidosException("El número de calle es obligatorio.");
    }

    if (pedidoDTO.getNombreCalle() == null || pedidoDTO.getNombreCalle().isBlank()) {
        throw new DatosInvalidosException("El nombre de calle es obligatorio.");
    }

    if (pedidoDTO.getIdComuna() <= 0) {
        throw new DatosInvalidosException("La comuna es inválida.");
    }

    // 2. Validar existencia del cliente
    ClienteDTO cliente = clienteClient.obtenerClienteByRut(pedidoDTO.getRutCliente());
    if (cliente == null) {
        throw new RecursoNoEncontradoException("No se encontró el cliente con RUT: " + pedidoDTO.getRutCliente());
    }

    // 3. Validar existencia de la comuna
    ComunaDTO comuna = ubicacionClient.obtenerComunasById(pedidoDTO.getIdComuna());
    if (comuna == null) {
        throw new RecursoNoEncontradoException("No se encontró la comuna con ID: " + pedidoDTO.getIdComuna());
    }

    // 4. Validar productos del detalle si existen
    if (pedidoDTO.getDetallePedido() != null) {
        for (DetallePedidoDTO detalle : pedidoDTO.getDetallePedido()) {
            if (detalle.getProductoId() <= 0) {
                throw new DatosInvalidosException("Producto inválido en el detalle.");
            }
            if (detalle.getCantidad() <= 0) {
                throw new DatosInvalidosException("La cantidad debe ser mayor que cero para el producto ID: " + detalle.getProductoId());
            }

            // Validar que el producto exista
            ProductoDTO producto = productoClient.obtenerProductoPorId(detalle.getProductoId());
            if (producto == null) {
                throw new RecursoNoEncontradoException("No se encontró el producto con ID: " + detalle.getProductoId());
            }
        }
    }

    // 5. Obtener centro de distribución por comuna
    CentroDistribucionDTO centro = centroDistribucionClient.obtenerCentroPorComuna(pedidoDTO.getIdComuna());
    if (centro == null) {
        throw new RecursoNoEncontradoException("No se encontró centro para la comuna ID: " + pedidoDTO.getIdComuna());
    }

    // 6. Obtener despachadores por centro
    List<UsuarioDTO> despachadores = usuarioClient.obtenerDespachadoresPorCentro(centro.getIdCentro());
    if (despachadores == null || despachadores.isEmpty()) {
        throw new RecursoNoEncontradoException("No se encontró despachador para el centro ID: " + centro.getIdCentro());
    }

    // 7. Seleccionar primer despachador
    UsuarioDTO despachador = despachadores.get(0);
    pedidoDTO.setIdDespachador(despachador.getId());

    // 8. Mapear PedidoDTO a PedidoEntity (sin detalles aún)
    PedidoEntity pedidoEntity = modelMapper.map(pedidoDTO, PedidoEntity.class);
    pedidoEntity.setIdCentro(centro.getIdCentro());
    pedidoEntity.setDetallePedido(null); // importante: evitar que Hibernate intente persistir antes

    // 9. Guardar PedidoEntity para obtener ID
    pedidoEntity = pedidoRepository.save(pedidoEntity);

    // 10. Mapear y asociar los detalles (si existen)
    if (pedidoDTO.getDetallePedido() != null && !pedidoDTO.getDetallePedido().isEmpty()) {
        Type listType = new TypeToken<List<DetallePedidoEntity>>() {}.getType();
        List<DetallePedidoEntity> detalles = modelMapper.map(pedidoDTO.getDetallePedido(), listType);

        for (DetallePedidoEntity detalle : detalles) {
            detalle.setPedidoId(pedidoEntity.getIdPedido());     // clave foránea
            detalle.setPedido(pedidoEntity);                     // relación bidireccional (si aplica)
        }

        // 11. Asignar detalles y guardar de nuevo (cascade)
        pedidoEntity.setDetallePedido(detalles);
        pedidoEntity = pedidoRepository.save(pedidoEntity);
    }

    // 12. Mapear PedidoEntity de vuelta a PedidoDTO
    PedidoDTO respuesta = modelMapper.map(pedidoEntity, PedidoDTO.class);
    return respuesta;
}



    @Override
    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PedidoDTO getPedidoById(int id) {
        PedidoEntity entity = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
        return modelMapper.map(entity, PedidoDTO.class);
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
