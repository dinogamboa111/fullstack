package cl.fullstack.pedido_ms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Column(name = "id_movimiento", nullable = false)
    private int idMovimiento;

    @Column(name = "id_usuario", nullable = false)
    private String idUsuario;

}
