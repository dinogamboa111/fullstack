package cl.fullstack.centro_distribucion_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comuna_cubierta")
@IdClass(ComunaCubiertaPKcompuesta.class)
public class ComunaCubiertaEntity implements Serializable {

    @Id
    @Column(name = "id_centro", nullable = false)
    private int idCentro;

    @Id
    @Column(name = "id_comuna", nullable = false)
    private int idComuna;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro", insertable = false, updatable = false)
    private CentroDistribucionEntity centroDistribucion;
}
