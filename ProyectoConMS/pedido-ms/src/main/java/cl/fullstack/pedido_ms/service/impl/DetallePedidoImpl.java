package cl.fullstack.pedido_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoEntity;
import cl.fullstack.pedido_ms.entity.DetallePedidoKey;
import cl.fullstack.pedido_ms.exception.DatosInvalidosException;
import cl.fullstack.pedido_ms.exception.RecursoDuplicadoException;
import cl.fullstack.pedido_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pedido_ms.repository.DetallePedidoRepository;
import cl.fullstack.pedido_ms.service.IDetallePedidoService;

@Service
public class DetallePedidoImpl implements IDetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Crea un nuevo detalle pedido, validando datos y evitando duplicados
    @Override
    public DetallePedidoDTO createDetallePedido(DetallePedidoDTO dto) {
        if (dto == null) {
            throw new DatosInvalidosException("Los datos del detalle pedido no pueden ser nulos");
        }

        validarDetallePedidoDTO(dto);

        DetallePedidoKey key = new DetallePedidoKey(dto.getPedidoId(), dto.getProductoId());

        if (detallePedidoRepository.existsById(key)) {
            throw new RecursoDuplicadoException("Detalle pedido ya existe para PedidoId: "
                    + dto.getPedidoId() + " y ProductoId: " + dto.getProductoId());
        }

        DetallePedidoEntity entity = modelMapper.map(dto, DetallePedidoEntity.class);
        DetallePedidoEntity guardado = detallePedidoRepository.save(entity);
        return modelMapper.map(guardado, DetallePedidoDTO.class);
    }

    // Obtiene un detalle pedido por su clave compuesta
    @Override
    public DetallePedidoDTO getDetallePedidoById(DetallePedidoKey id) {
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));
        return modelMapper.map(entity, DetallePedidoDTO.class);
    }

    // Obtiene todos los detalles pedidos
    @Override
    public List<DetallePedidoDTO> getAllDetallePedidos() {
        return detallePedidoRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, DetallePedidoDTO.class))
                .collect(Collectors.toList());
    }

    // Actualiza un detalle pedido existente, sin modificar la clave primaria
    @Override
    public DetallePedidoDTO updateDetallePedido(DetallePedidoKey id, DetallePedidoDTO dto) {
        if (dto == null) {
            throw new DatosInvalidosException("Los datos del detalle pedido no pueden ser nulos");
        }

        validarDetallePedidoDTO(dto);

        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));

        entity.setCantidad(dto.getCantidad());

        DetallePedidoEntity actualizado = detallePedidoRepository.save(entity);
        return modelMapper.map(actualizado, DetallePedidoDTO.class);
    }

    // Elimina un detalle pedido dado su ID compuesto
    public String eliminarDetallePedido(DetallePedidoKey id) {
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));
        detallePedidoRepository.delete(entity);
        return "Detalle pedido eliminado con éxito";
    }

    // Valida que los datos del DTO sean correctos
    private void validarDetallePedidoDTO(DetallePedidoDTO dto) {
        if (dto.getPedidoId() <= 0) {
            throw new DatosInvalidosException("El ID del pedido debe ser mayor a cero.");
        }
        if (dto.getProductoId() <= 0) {
            throw new DatosInvalidosException("El ID del producto debe ser mayor a cero.");
        }
        if (dto.getCantidad() <= 0) {
            throw new DatosInvalidosException("La cantidad debe ser mayor a cero.");
        }
    }

}
