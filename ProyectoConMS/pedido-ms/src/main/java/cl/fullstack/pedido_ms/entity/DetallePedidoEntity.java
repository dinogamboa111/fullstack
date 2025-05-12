package cl.fullstack.pedido_ms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"pedidoId", "productoId"})
@Table(name = "detalle_pedido")
@IdClass(DetallePedidoKey.class)

public class DetallePedidoEntity {

    @Id
    @Column(name = "PEDIDO_id_pedido", nullable = false)
    private int pedidoId;

    @Id
    @Column(name = "PRODUCTO_id_producto", nullable = false)
    private int productoId;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio", nullable = false)
    private double precio;

    @ManyToOne
@JoinColumn(name = "PEDIDO_id_pedido", insertable = false, updatable = false)
private PedidoEntity pedido;



}
