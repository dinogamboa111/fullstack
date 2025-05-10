package cl.fullstack.pedido_ms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class DetallePedidoKey implements Serializable {

    @Column(name = "PEDIDO_id_pedido")
    private Long pedidoId;

    @Column(name = "PRODUCTO_id_producto")
    private Long productoId;
}
