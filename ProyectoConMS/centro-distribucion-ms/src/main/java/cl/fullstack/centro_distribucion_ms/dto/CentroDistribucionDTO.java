package cl.fullstack.centro_distribucion_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data // genera getters, setters, toString y otros metodos automaticamente
@NoArgsConstructor // genera constructor vacio
@AllArgsConstructor // genera constructor con todos los campos

public class CentroDistribucionDTO {

    private int idCentro; // id unico del centro de distribucion

    private String nombreCentro; // nombre del centro

    private String direccion; // direccion del centro

    private int idComuna; // id de la comuna base del centro

    private String nombreComuna; // nombre de la comuna base

    private String nombreProvincia; // nombre de la provincia base

    private String nombreRegion; // nombre de la region base

    private List<ComunaCubiertaDTO> comunasCubiertas; // lista de comunas que cubre este centro
}
