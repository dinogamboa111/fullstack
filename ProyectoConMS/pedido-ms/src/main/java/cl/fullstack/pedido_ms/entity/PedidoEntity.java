package cl.fullstack.pedido_ms.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    @SequenceGenerator(name = "pedido_seq", sequenceName = "PEDIDO_SEQ", allocationSize = 1)
    @Column(name = "id_pedido")
    private int idPedido;

    @Column(name = "rut_cliente", nullable = false)
    private int rutCliente;

    @Column(name = "dv_cliente", nullable = false)
    private char dvCliente;

    @Column(name = "num_calle", nullable = false)
    private String numCalle;

    @Column(name = "nombre_calle", nullable = false)
    private String nombreCalle;

    @Column(name = "id_comuna", nullable = false)
    private int idComuna;

    @Column(name = "estado_pedido", nullable = false)
    private boolean estadoPedido;

    // Relación con detalle pedido (uno a muchos), un pedido puede tener muchos
    // detalles
    // CascadeType.ALL*: con esto digo que cualquier operacion que se haga en
    // pedidoEntity tb debe aaplicarse a todos los detallesPedidos asociados
    // asi cuando se genera un pedido, no debo estar llaamando al metodo
    // guardardetalle
    // orphanRemoval* = true, esto es que si se elimina un detallepedido de la
    // lista, tb se debe eliminar de la bbdd
    // * con esto, tal vez no es necesario un crud independiente para detallepedido,
    // porque todo lo que contemple detallepedido se puede manejar desde pedido
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id_pedido") // debe coincidir EXACTAMENTE con la FK en detalle_pedido
    private List<DetallePedidoEntity> detallePedido;

    @Column(name = "id_despachador", nullable = false)
    private Integer idDespachador;

}
