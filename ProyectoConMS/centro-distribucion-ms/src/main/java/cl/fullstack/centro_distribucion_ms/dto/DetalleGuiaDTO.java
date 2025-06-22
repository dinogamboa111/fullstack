package cl.fullstack.centro_distribucion_ms.dto;


import lombok.Data;

@Data
public class DetalleGuiaDTO {
    private int idDetalle;     // opcional si lo necesitas en la respuesta
    private int idPedido;   // ID del pedido asociado
}
