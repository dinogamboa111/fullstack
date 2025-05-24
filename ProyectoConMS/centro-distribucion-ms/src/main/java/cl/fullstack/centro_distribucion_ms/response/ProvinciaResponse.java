package cl.fullstack.centro_distribucion_ms.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // ignora campos desconocidos para mayor flexibilidad
public class ProvinciaResponse {
    private int idProvincia; // id de la provincia dentro de la region
    private String nombre; // nombre de la provincia
    private RegionResponse region; // objeto region con datos de la region a la que pertenece la provincia
}