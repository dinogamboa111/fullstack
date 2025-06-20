package cl.fullstack.centro_distribucion_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "centro_distribucion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentroDistribucionEntity {

    @Id
    @Column(name = "id_centro", nullable = false)
    private int idCentro;

    @Column(name = "nombre_centro", nullable = false)
    private String nombreCentro;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    @Column(name = "id_comuna", nullable = false)
    private int idComuna;

    @Column(name = "nombre_comuna", nullable = false)
    private String nombreComuna;

    @Column(name = "nombre_provincia", nullable = false)
    private String nombreProvincia;

    @Column(name = "nombre_region", nullable = false)
    private String nombreRegion;

    @OneToMany(mappedBy = "centroDistribucion", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ComunaCubiertaEntity> comunasCubiertas;
}

