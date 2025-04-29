package cl.fullstack.pedido_ms.service;

import java.util.List;

import cl.fullstack.pedido_ms.dto.PedidoDTO;

public interface IPedidoService {

    
    List<PedidoDTO> getAllPedidos();
    PedidoDTO getPedidoById(int idPedido);
    PedidoDTO createPedido(PedidoDTO pedidoDTO);
    PedidoDTO updatePedido(int idPEdido, PedidoDTO pedidoDTO);
    void deletePedido(int idPedido);
   


}


