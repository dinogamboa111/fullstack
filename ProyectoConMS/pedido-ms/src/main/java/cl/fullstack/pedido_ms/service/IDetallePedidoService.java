package cl.fullstack.pedido_ms.service;

import java.util.List;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoId;

public interface IDetallePedidoService {

    DetallePedidoDTO createDetallePedido(DetallePedidoDTO dto);

    DetallePedidoDTO getDetallePedidoById(DetallePedidoId id);

    List<DetallePedidoDTO> getAllDetallePedidos();

    DetallePedidoDTO updateDetallePedido(DetallePedidoId id, DetallePedidoDTO dto);

    void deleteDetallePedido(DetallePedidoId id);
}
