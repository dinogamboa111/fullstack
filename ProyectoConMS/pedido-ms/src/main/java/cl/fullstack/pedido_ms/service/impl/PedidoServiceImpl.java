package cl.fullstack.pedido_ms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // metodo para crear pedido 
    @Transactional
    @Override
    public PedidoDTO createPedido(PedidoDTO pedidoDTO) {
        // 1. primero realizamos validaciones basicas para capturas datos invalidos
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

        // 2.validamos que el cliente exista en cliente-ms, lanzamos recurso no
        // encontrado si es que no existe
        ClienteDTO cliente = clienteClient.obtenerClienteByRut(pedidoDTO.getRutCliente());
        if (cliente == null) {
            throw new RecursoNoEncontradoException("No se encontró el cliente con RUT: " + pedidoDTO.getRutCliente());
        }

        // 3.validamos que la comuna exista en ubicacion-ms, lanzamos recurso no
        // encontrado si es que no existe
        ComunaDTO comuna = ubicacionClient.obtenerComunasById(pedidoDTO.getIdComuna());
        if (comuna == null) {
            throw new RecursoNoEncontradoException("No se encontró la comuna con ID: " + pedidoDTO.getIdComuna());
        }

        // 4. validamos que los datos sean validos en el detalle, si son invalidos,
        // lanzamos datosInvalidosExepcion
        if (pedidoDTO.getDetallePedido() != null) {
            for (DetallePedidoDTO detalle : pedidoDTO.getDetallePedido()) {
                if (detalle.getProductoId() <= 0) {
                    throw new DatosInvalidosException("Producto inválido en el detalle.");
                }
                if (detalle.getCantidad() <= 0) {
                    throw new DatosInvalidosException(
                            "La cantidad debe ser mayor que cero para el producto ID: " + detalle.getProductoId());
                }

                // validamos que los productos existan en producto-ms, si no existen, se lanza
                // RecursoNoEncontradoException
                ProductoDTO producto = productoClient.obtenerProductoPorId(detalle.getProductoId());
                if (producto == null) {
                    throw new RecursoNoEncontradoException(
                            "No se encontró el producto con ID: " + detalle.getProductoId());
                }
            }
        }

        // 5. Buscamos el centro de distribucion segun la comuna ingresada
        CentroDistribucionDTO centro = centroDistribucionClient.obtenerCentroPorComuna(pedidoDTO.getIdComuna());
        if (centro == null) {
            throw new RecursoNoEncontradoException(
                    "No se encontró centro para la comuna ID: " + pedidoDTO.getIdComuna());
        }

        // 6. Buscamos el despachador asociado a ese centro
        List<UsuarioDTO> despachadores = usuarioClient.obtenerDespachadoresPorCentro(centro.getIdCentro());
        if (despachadores == null || despachadores.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No se encontró despachador para el centro ID: " + centro.getIdCentro());
        }

        // 7. Seleccionar primer despachador
        UsuarioDTO despachador = despachadores.get(0);
        pedidoDTO.setIdDespachador(despachador.getId());

        // 8. Mapear PedidoDTO a PedidoEntity (sin detalles aún)
        PedidoEntity pedidoEntity = modelMapper.map(pedidoDTO, PedidoEntity.class);
        pedidoEntity.setIdCentro(centro.getIdCentro());
        pedidoEntity.setDetallePedido(null); // importante: evitar que Hibernate intente persistir antes

        // 9. Guardar PedidoEntity para obtener ID, guardamos primero el pedido base sin
        // el detalle para poder obtener el id
        pedidoEntity = pedidoRepository.save(pedidoEntity);

        // 10. ahora si hay detalles los mapeamos
        if (pedidoDTO.getDetallePedido() != null && !pedidoDTO.getDetallePedido().isEmpty()) {
            // aqui definimos el tipo completo de lista de entidades para que modelmapper no pierda el tipo generico 
            // cuando el programa se esta ejecutando, java no recuerda que la lista es List<DetallePedidoEntity>, simplemente piensa que es List
            // esto de llama type erasure(borrado de tipos): java borra la informacion de los tipos genericos al ejecutar el programa
            // es un problema porque al usar modelmapper, java ya olvido que tipo de dato debe ir dentro -> (List<DetallePedidoEntity>)
            // entonces usamos typeToken, para darle una pista de lo que es -> oye model, esto es una lista de detallepedidoentity tenlo claro
            Type listType = new TypeToken<List<DetallePedidoEntity>>() {
            }.getType();
            // transformamos la dto en entidades jpa
            List<DetallePedidoEntity> detalles = modelMapper.map(pedidoDTO.getDetallePedido(), listType);
            // asignamos la relacion a cada detalle del pedido
            for (DetallePedidoEntity detalle : detalles) {
                detalle.setPedidoId(pedidoEntity.getIdPedido()); // clave foránea
                detalle.setPedido(pedidoEntity); // relación bidireccional (si aplica)
            }

            // 11. asignamos los detalles al pedido y nuevamente guardamos
            pedidoEntity.setDetallePedido(detalles);
            pedidoEntity = pedidoRepository.save(pedidoEntity);
        }

        // 12. Mapear PedidoEntity de vuelta a PedidoDTO para mostrarla de salida
        PedidoDTO respuesta = modelMapper.map(pedidoEntity, PedidoDTO.class);
        return respuesta;
    }

    // metodo para obtener todos los pedidos 
    @Override
    public List<PedidoDTO> getAllPedidos() {
        return pedidoRepository.findAll().stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    // metodo para obtener pedido por id 
    @Override
    public PedidoDTO getPedidoById(int id) {
        PedidoEntity entity = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado con id: " + id));
        return modelMapper.map(entity, PedidoDTO.class);
    }

    // método para actualizar pedido
    @Override
    @Transactional
    public PedidoDTO updatePedido(int id, PedidoDTO dto) {
        // 1. Verificamos si el pedido existe
        PedidoEntity pedidoExistente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));

        // 2. Validaciones básicas de datos del pedido
        if (dto.getRutCliente() <= 0) {
            throw new DatosInvalidosException("El RUT del cliente debe ser mayor que cero.");
        }

        if (dto.getDvCliente() == '\0' || Character.isWhitespace(dto.getDvCliente())) {
            throw new DatosInvalidosException("El dígito verificador del cliente es obligatorio.");
        }

        if (dto.getNumCalle() == null || dto.getNumCalle().isBlank()) {
            throw new DatosInvalidosException("El número de calle es obligatorio.");
        }

        if (dto.getNombreCalle() == null || dto.getNombreCalle().isBlank()) {
            throw new DatosInvalidosException("El nombre de calle es obligatorio.");
        }

        if (dto.getIdComuna() <= 0) {
            throw new DatosInvalidosException("La comuna es inválida.");
        }

        // 3. Validamos existencia del cliente
        ClienteDTO cliente = clienteClient.obtenerClienteByRut(dto.getRutCliente());
        if (cliente == null) {
            throw new RecursoNoEncontradoException("No se encontró el cliente con RUT: " + dto.getRutCliente());
        }

        // 4. Validamos existencia de la comuna
        ComunaDTO comuna = ubicacionClient.obtenerComunasById(dto.getIdComuna());
        if (comuna == null) {
            throw new RecursoNoEncontradoException("No se encontró la comuna con ID: " + dto.getIdComuna());
        }

        // 5. Validamos existencia y consistencia de los productos en el detalle
        if (dto.getDetallePedido() != null) {
            for (DetallePedidoDTO detalle : dto.getDetallePedido()) {
                if (detalle.getProductoId() <= 0) {
                    throw new DatosInvalidosException("Producto inválido en el detalle.");
                }
                if (detalle.getCantidad() <= 0) {
                    throw new DatosInvalidosException(
                            "La cantidad debe ser mayor que cero para el producto ID: " + detalle.getProductoId());
                }

                ProductoDTO producto = productoClient.obtenerProductoPorId(detalle.getProductoId());
                if (producto == null) {
                    throw new RecursoNoEncontradoException(
                            "No se encontró el producto con ID: " + detalle.getProductoId());
                }
            }
        }

        // 6. Obtener nuevo centro de distribución y despachador según la comuna
        CentroDistribucionDTO centro = centroDistribucionClient.obtenerCentroPorComuna(dto.getIdComuna());
        if (centro == null) {
            throw new RecursoNoEncontradoException("No se encontró centro para la comuna ID: " + dto.getIdComuna());
        }

        List<UsuarioDTO> despachadores = usuarioClient.obtenerDespachadoresPorCentro(centro.getIdCentro());
        if (despachadores == null || despachadores.isEmpty()) {
            throw new RecursoNoEncontradoException(
                    "No se encontró despachador para el centro ID: " + centro.getIdCentro());
        }

        UsuarioDTO despachador = despachadores.get(0);

        // 7. Actualizamos los campos del pedido
        pedidoExistente.setRutCliente(dto.getRutCliente());
        pedidoExistente.setDvCliente(dto.getDvCliente());
        pedidoExistente.setNombreCalle(dto.getNombreCalle());
        pedidoExistente.setNumCalle(dto.getNumCalle());
        pedidoExistente.setIdComuna(dto.getIdComuna());
        pedidoExistente.setIdCentro(centro.getIdCentro());
        pedidoExistente.setIdDespachador(despachador.getId());
        pedidoExistente.setEstadoPedido(dto.isEstadoPedido());

        // 8. Si hay detalles en la petición, los actualizamos limpiando y agregando a la misma lista
        if (dto.getDetallePedido() != null) {
            // Definimos el tipo exacto para evitar borrado de tipos en runtime (type erasure)
            Type listType = new TypeToken<List<DetallePedidoEntity>>() {
            }.getType();
            List<DetallePedidoEntity> nuevosDetalles = modelMapper.map(dto.getDetallePedido(), listType);

            // Limpiamos la lista actual SIN reemplazarla, así evitamos el error de Hibernate
            pedidoExistente.getDetallePedido().clear();

            // Asociamos cada detalle al pedido actual
            for (DetallePedidoEntity detalle : nuevosDetalles) {
                detalle.setPedidoId(pedidoExistente.getIdPedido()); // asigna la clave foránea
                detalle.setPedido(pedidoExistente); // referencia bidireccional (si está presente en entidad)
                pedidoExistente.getDetallePedido().add(detalle); // agregamos a la lista actual
            }
        }

        // 9. Guardamos los cambios
        PedidoEntity actualizado = pedidoRepository.save(pedidoExistente);

        // 10. Mapeamos y retornamos el DTO actualizado
        return modelMapper.map(actualizado, PedidoDTO.class);
    }

    // metodo para eliminar pedido 
    @Override
    public String deletePedido(int id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con ID: " + id));
        pedidoRepository.delete(pedido);
        return "Pedido eliminado con éxito";
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
