package cl.fullstack.pedido_ms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoEntity;
import cl.fullstack.pedido_ms.entity.DetallePedidoId;
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

        // Construimos la clave compuesta
        DetallePedidoId id = new DetallePedidoId();
        id.setPedidoId(dto.getPedidoId());
        id.setProductoId(dto.getProductoId());

        entity.setId(id);
        entity.setIdDetalle(dto.getIdDetalle());
        entity.setCantidad(dto.getCantidad());
        entity.setPrecio(dto.getPrecio());

        DetallePedidoEntity saved = detallePedidoRepository.save(entity);
        return modelMapper.map(saved, DetallePedidoDTO.class);
    }

    @Override
    public DetallePedidoDTO getDetallePedidoById(DetallePedidoId id) {
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
    public DetallePedidoDTO updateDetallePedido(DetallePedidoId id, DetallePedidoDTO dto) {
        detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));

        DetallePedidoEntity entity = new DetallePedidoEntity();
        entity.setId(id);
        entity.setIdDetalle(dto.getIdDetalle());
        entity.setCantidad(dto.getCantidad());
        entity.setPrecio(dto.getPrecio());

        DetallePedidoEntity updated = detallePedidoRepository.save(entity);
        return modelMapper.map(updated, DetallePedidoDTO.class);
    }

    @Override
    public void deleteDetallePedido(DetallePedidoId id) {
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));
        detallePedidoRepository.delete(entity);
    }
}
