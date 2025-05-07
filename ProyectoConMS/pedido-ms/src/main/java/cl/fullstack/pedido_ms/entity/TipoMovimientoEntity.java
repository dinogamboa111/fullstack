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
@Table(name = "tipo_movimiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoMovimientoEntity {

    @Id
    @Column(name = "id_movimiento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipoMovimiento_seq")
    @SequenceGenerator(name = "tipoMovimiento_seq", sequenceName = "TIPOMOVIMIENTO_SEQ", allocationSize = 1)
    private int idMovimiento;

    @Column(name = "rut_cliente", nullable = false)
    private String nombreMovimiento;

}
