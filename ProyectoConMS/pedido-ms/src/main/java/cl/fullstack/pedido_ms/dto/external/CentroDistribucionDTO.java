package cl.fullstack.pedido_ms.dto.external;

import lombok.Data;

@Data
public class CentroDistribucionDTO {
    private Integer idCentro;
    private String nombreCentro;
    private String direccion;
    private Integer idComuna;
    private String nombreComuna;
    private String nombreProvincia;
    private String nombreRegion;
}