package cl.fullstack.centro_distribucion_ms.dto;


import lombok.Data;

@Data
public class DetalleGuiaDTO {
    private Long idDetalle;     // opcional si lo necesitas en la respuesta
    private Integer idPedido;   // ID del pedido asociado
}
