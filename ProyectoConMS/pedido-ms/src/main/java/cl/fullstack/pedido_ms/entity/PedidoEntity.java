package cl.fullstack.pedido_ms.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToOne;
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
    @Column(name = "id_pedido")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedido_seq")
    @SequenceGenerator(name = "pedido_seq", sequenceName = "PEDIDO_SEQ", allocationSize = 1)
    private int idPedido;

    @Column(name = "rut_cliente", nullable = false)
    private int rutCliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_movimiento", nullable = false)
    private TipoMovimientoEntity idMovimiento;

    @Column(name = "id_usuario", nullable = false)
    private int idUsuario;

    //Significa que todas las operaciones (persistir, eliminar, actualizar, etc.)
    // que se hagan sobre un PedidoEntity también se harán sobre los DetallePedidoEntity relacionados automáticamente.
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedidoEntity> productos;

}

