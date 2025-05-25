package cl.fullstack.centro_distribucion_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // genera getters, setters y otros metodos automaticamente
@NoArgsConstructor // genera constructor vacio
@AllArgsConstructor // genera constructor con todos los campos

public class ComunaCubiertaDTO {

    private int idCentro; // id del centro de distribucion asociado

    private int idComuna; // id de la comuna cubierta
}
