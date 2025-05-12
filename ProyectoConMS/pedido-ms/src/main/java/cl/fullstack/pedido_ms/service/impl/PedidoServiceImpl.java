package cl.fullstack.pedido_ms.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.dto.PedidoDTO;
import cl.fullstack.pedido_ms.dto.ProductoDTO;
import cl.fullstack.pedido_ms.dto.TipoMovimientoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoEntity;
import cl.fullstack.pedido_ms.entity.DetallePedidoKey;
import cl.fullstack.pedido_ms.entity.PedidoEntity;
import cl.fullstack.pedido_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pedido_ms.repository.DetallePedidoRepository;
import cl.fullstack.pedido_ms.repository.PedidoRepository;
import cl.fullstack.pedido_ms.service.IPedidoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
// nueva
@RequiredArgsConstructor

public class PedidoServiceImpl implements IPedidoService {

    // nueva
    private final RestTemplate restTemplate;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
private DetallePedidoRepository detallePedidoRepository;

    // este es metodo original
    @Override
    public PedidoDTO createPedido(PedidoDTO dto) {
     PedidoEntity pedido = modelMapper.map(dto, PedidoEntity.class);
    return modelMapper.map(pedidoRepository.save(pedido), PedidoDTO.class);
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
        pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrada con ID: " + id));
        dto.setRutCliente(id); // aseguramos que no cambie el ID
        PedidoEntity actualizado = modelMapper.map(dto, PedidoEntity.class);
        return modelMapper.map(pedidoRepository.save(actualizado), PedidoDTO.class);
    }

    @Override
    public void deletePedido(int id) {
        PedidoEntity pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrada con ID: " + id));
        pedidoRepository.delete(pedido);
    }

}
