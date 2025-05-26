package cl.fullstack.centro_distribucion_ms.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // ignora campos extra en la respuesta json
public class RegionResponse {
    private int idRegion; // id de la region
    private String nombre; // nombre de la region
}
