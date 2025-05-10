package cl.fullstack.pedido_ms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;




@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_pedido")
//con @idclass le estoy indicando que esta entidad tiene una clave compuesta, definidos en esta clase auxiliar ->DetallePedidoKey
@IdClass(DetallePedidoKey.class)
public class DetallePedidoEntity {
    //ahora con lo anterior, puedo aqui poner @id y en el otro atributo, asi sabe que esos son los pk q conforman la clave compuesta
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
}
