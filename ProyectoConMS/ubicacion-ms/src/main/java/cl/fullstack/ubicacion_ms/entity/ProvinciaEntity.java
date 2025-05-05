package cl.fullstack.ubicacion_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "provincia")
public class ProvinciaEntity {
    @Id
    @Column(name = "id_provincia")
    private int idProvincia;
    
    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_region", nullable = false)
    private RegionEntity region;
}
