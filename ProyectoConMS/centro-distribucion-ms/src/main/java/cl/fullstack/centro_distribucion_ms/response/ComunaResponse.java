package cl.fullstack.centro_distribucion_ms.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // ignora campos desconocidos al mapear json para evitar errores
public class ComunaResponse {
    private int idComuna; // id de la comuna que llega desde el servicio ubicacion-ms
    private String nombre; // nombre de la comuna que llega desde el servicio ubicacion-ms
    private ProvinciaResponse provincia; // objeto provincia que contiene info de la provincia de la comuna
}

///////////////////////


//package cl.fullstack.centro_distribucion_ms.response;

//import lombok.Data;

//@Data
//public class ComunaResponse {
   // private int idComuna;  // id de la comuna que llega desde el servicio ubicacion-ms
   // private String nombre; // nombre de la comuna que llega desde el servicio ubicacion-ms
//}
