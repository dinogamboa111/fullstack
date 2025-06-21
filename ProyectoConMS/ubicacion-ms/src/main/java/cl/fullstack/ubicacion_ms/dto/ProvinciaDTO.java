package cl.fullstack.ubicacion_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvinciaDTO {
    private int idProvincia;
    private String nombre;
    private RegionDTO region; // Relacion con RegionDTO
}
