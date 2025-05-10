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
        entity.setPedidoId(dto.getPedidoId());
        entity.setProductoId(dto.getProductoId());
        entity.setCantidad(dto.getCantidad());
        entity.setPrecio(dto.getPrecio());

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
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));

        // Actualizamos solo los campos que se pueden modificar
        entity.setCantidad(dto.getCantidad());
        entity.setPrecio(dto.getPrecio());

        DetallePedidoEntity updated = detallePedidoRepository.save(entity);
        return modelMapper.map(updated, DetallePedidoDTO.class);
    }

    @Override
    public void deleteDetallePedido(DetallePedidoKey id) {
        DetallePedidoEntity entity = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Detalle pedido no encontrado con ID: " + id));
        detallePedidoRepository.delete(entity);
    }
}
