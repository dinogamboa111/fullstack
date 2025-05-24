package cl.fullstack.centro_distribucion_ms.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class ComunaCubiertaPKcompuesta implements Serializable {  //esta clase convierte a idCentro e Idcomuna en Serializable, para usar ambos como primary key
    private int idCentro;
    private int idComuna;
}