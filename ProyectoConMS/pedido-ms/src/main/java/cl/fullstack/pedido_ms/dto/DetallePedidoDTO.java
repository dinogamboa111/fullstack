package cl.fullstack.pedido_ms.dto;

import lombok.Data;

@Data

public class DetallePedidoDTO {

    private int pedidoId;
    private int productoId;
    private int cantidad;


}
