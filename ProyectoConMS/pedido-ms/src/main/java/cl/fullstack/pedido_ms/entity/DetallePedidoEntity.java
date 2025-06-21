
package cl.fullstack.pedido_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "detalle_pedido")
@IdClass(DetallePedidoKey.class)
public class DetallePedidoEntity {

    @Id
    @Column(name = "PEDIDO_id_pedido")
    private int pedidoId;

    @Id
    @Column(name = "PRODUCTO_id_producto")
    private int productoId;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pedidoId") // Indica que esta relación usa la parte pedidoId de la clave compuesta
    @JoinColumn(name = "PEDIDO_id_pedido")
    private PedidoEntity pedido;
}
