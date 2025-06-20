package cl.fullstack.pedido_ms.service;

import java.util.List;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoKey;

public interface IDetallePedidoService {

    DetallePedidoDTO createDetallePedido(DetallePedidoDTO dto);

    DetallePedidoDTO getDetallePedidoById(DetallePedidoKey id);

    List<DetallePedidoDTO> getAllDetallePedidos();

    DetallePedidoDTO updateDetallePedido(DetallePedidoKey id, DetallePedidoDTO dto);

    void deleteDetallePedido(DetallePedidoKey id);
}
