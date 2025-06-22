package cl.fullstack.pedido_ms.service;

import java.util.List;

import cl.fullstack.pedido_ms.dto.PedidoDTO;
import cl.fullstack.pedido_ms.entity.PedidoEntity;

public interface IPedidoService {

    List<PedidoDTO> getAllPedidos();

    PedidoDTO getPedidoById(int idPedido);

    PedidoDTO createPedido(PedidoDTO pedidoDTO);

    PedidoDTO updatePedido(int idPEdido, PedidoDTO pedidoDTO);

    String deletePedido(int idPedido);
    
    List<PedidoEntity> obtenerPedidosPorDespachador(Integer idDespachador);

}
