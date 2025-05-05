package cl.fullstack.pedido_ms.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import cl.fullstack.pedido_ms.dto.PedidoDTO;
import cl.fullstack.pedido_ms.entity.PedidoEntity;
import cl.fullstack.pedido_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.pedido_ms.repository.PedidoRepository;
import cl.fullstack.pedido_ms.service.IPedidoService;

@Service

public class PedidoServiceImpl implements IPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ModelMapper modelMapper;

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
