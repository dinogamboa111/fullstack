package cl.fullstack.pedido_ms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@NoArgsConstructor
@AllArgsConstructor

@Data
@Entity
@Table(name = "detalle_pedido")
public class DetallePedidoEntity {

    @EmbeddedId
    private DetallePedidoId id;

    @Column(name = "id_detalle", nullable = false)
    private int idDetalle;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio", nullable = false)
    private int precio;

}
