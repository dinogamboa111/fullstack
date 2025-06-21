package cl.fullstack.centro_distribucion_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // genera getters, setters, toString y otros metodos automaticamente
@NoArgsConstructor // genera constructor vacio
@AllArgsConstructor // genera constructor con todos los campos

public class CentroDistribucionDTO {
    private int idCentro;
    private String nombreCentro;
    private String direccion;
    private int idComuna;
    private String nombreComuna;
    private String nombreProvincia;
    private String nombreRegion;
}