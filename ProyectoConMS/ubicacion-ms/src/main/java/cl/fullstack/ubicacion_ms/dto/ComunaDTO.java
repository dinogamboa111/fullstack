package cl.fullstack.ubicacion_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaDTO {
    
    private int idComuna;
    private String nombre;
    private ProvinciaDTO provincia; // Relacion con ProvinciaDTO
}
