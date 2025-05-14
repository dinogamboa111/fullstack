
// package cl.fullstack.pedido_ms.entity;

// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;
// import jakarta.persistence.*;

// @NoArgsConstructor
// @AllArgsConstructor

// @Data
// @Entity
// @Table(name = "detalle_pedido")
// public class DetallePedidoEntity {

//     @EmbeddedId
//     private DetallePedidoKey id;

//     @Column(name = "id_detalle", nullable = false)
//     private int idDetalle;

//     @Column(name = "cantidad", nullable = false)
//     private int cantidad;

//     @Column(name = "precio", nullable = false)
//     private int precio;

// }

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

    @Column(name = "id_detalle", nullable = false)
    private int idDetalle;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio", nullable = false)
    private int precio;

 
}
