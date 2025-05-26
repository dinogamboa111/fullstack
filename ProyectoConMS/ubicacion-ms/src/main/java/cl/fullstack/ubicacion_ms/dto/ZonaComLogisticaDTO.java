package cl.fullstack.ubicacion_ms.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ZonaComLogisticaDTO {
    
    @NotNull(message = "El identificador de Comuna no puede ser nulo")
    private int idComuna;
    
    @NotNull(message = "El nombre de Comuna no puede ser nulo")
    private String nombreComuna;
    
    @NotNull(message = "El codigo de Zona,para logistica no puede ser nulo")
    private String codigoZona;

    @NotNull(message = "El nombre para de zona para logistica no puede ser nulo")
    private String nombreZona;
}


