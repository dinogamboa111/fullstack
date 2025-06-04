package cl.fullstack.pedido_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoEntity;
import cl.fullstack.pedido_ms.entity.DetallePedidoKey;
import cl.fullstack.pedido_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pedido_ms.repository.DetallePedidoRepository;
import cl.fullstack.pedido_ms.service.IDetallePedidoService;

@Service
public class DetallePedidoImpl implements IDetallePedidoService {

    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DetallePedidoDTO createDetallePedido(DetallePedidoDTO dto) {
        DetallePedidoEntity entity = new DetallePedidoEntity();

        // Asignamos las claves primarias
        entity.setPedidoId(dto.getPedidoId()); // asigna pedidoId
        entity.setProductoId(dto.getProductoId()); // asigna productoId

        entity.setCantidad(dto.getCantidad());

        DetallePedidoEntity saved = detallePedidoRepository.save(entity);
        return modelMapper.map(saved, DetallePedidoDTO.class);
    }

    @Override
    public DetallePedidoDTO getDetallePedidoById(DetallePedidoKey id) {
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));
        return modelMapper.map(entity, DetallePedidoDTO.class);
    }

    @Override
    public List<DetallePedidoDTO> getAllDetallePedidos() {
        return detallePedidoRepository.findAll().stream()
                .map(entity -> modelMapper.map(entity, DetallePedidoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public DetallePedidoDTO updateDetallePedido(DetallePedidoKey id, DetallePedidoDTO dto) {
        // Buscamos el detalle de pedido con la clave compuesta
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));

        // Actualizamos los campos de la entidad
        entity.setPedidoId(id.getPedidoId()); // asignamos el pedidoId
        entity.setProductoId(id.getProductoId()); // asignamos el productoId

        entity.setCantidad(dto.getCantidad());

        // Guardamos la entidad actualizada
        DetallePedidoEntity updated = detallePedidoRepository.save(entity);

        // Devolvemos el DTO actualizado
        return modelMapper.map(updated, DetallePedidoDTO.class);
    }

    @Override
    public void deleteDetallePedido(DetallePedidoKey id) {
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));
        detallePedidoRepository.delete(entity);
    }
}
