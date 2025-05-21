package cl.fullstack.centro_distribucion_ms.response;

import lombok.Data;

@Data
public class ComunaResponse {
    private int idComuna;  // id de la comuna que llega desde el servicio ubicacion-ms
    private String nombre; // nombre de la comuna que llega desde el servicio ubicacion-ms
}
