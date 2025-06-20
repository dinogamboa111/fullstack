package cl.fullstack.ubicacion_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comuna")
public class ComunaEntity {
    @Id
    @Column(name = "id_comuna", nullable = false)
    private int idComuna;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_provincia", nullable = false)
    private ProvinciaEntity provincia;
}