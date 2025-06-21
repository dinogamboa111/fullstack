package cl.fullstack.usuario_ms.dto.external;

import lombok.Data;

@Data
public class CentroDistribucionDTO {
    private int idCentro;
    private String nombreCentro;
    private String direccion;
    private int idComuna;
    // agrega más campos si necesitas
}