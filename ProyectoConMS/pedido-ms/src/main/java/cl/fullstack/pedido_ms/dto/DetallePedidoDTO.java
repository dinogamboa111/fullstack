package cl.fullstack.pedido_ms.dto;

import lombok.Data;

@Data

public class DetallePedidoDTO {

    private Long pedidoId;
    private Long productoId;
    private Long idDetalle;
    private int cantidad;
    private double precio;

}
