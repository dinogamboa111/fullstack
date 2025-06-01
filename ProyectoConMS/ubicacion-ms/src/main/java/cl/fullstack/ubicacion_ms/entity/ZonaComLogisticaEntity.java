package cl.fullstack.ubicacion_ms.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Zona_Logistica_Comuna")

public class ZonaComLogisticaEntity {
 @Id
    @Column(name = "id_comuna")
    private int idComuna;
    
    @Column(name = "nombre_comuna", nullable = false)
    private String nombreComuna;

    @Column(name = "codigo_zona", nullable = false)
    private String codigoZona;

   @Column(name = "nombre_zona", nullable = false)
    private String nombreZona;

    }


