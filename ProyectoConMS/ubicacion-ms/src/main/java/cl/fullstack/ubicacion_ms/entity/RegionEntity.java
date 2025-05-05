
package cl.fullstack.ubicacion_ms.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "region")
public class RegionEntity {
    @Id
    @Column(name = "id_region")  
    private int idRegion;  
    
    @Column(nullable = false)
    private String nombre;
}










