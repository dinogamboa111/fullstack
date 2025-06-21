package cl.fullstack.usuario_ms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "rol")
public class RolEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_seq")
    @SequenceGenerator(name = "rol_seq", sequenceName = "ROL_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String nombre; // Ej: "DESPACHADOR", "ADMIN"
    @Column(nullable = false)
    private String descripcion;

}
