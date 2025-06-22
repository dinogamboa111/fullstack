package cl.fullstack.centro_distribucion_ms.dto;

import lombok.Data;
import java.util.List;

@Data
public class GuiaDespachoDTO {
    private int idGuia;                     
    private int idDespachador;         
    private List<DetalleGuiaDTO> pedidosAsociados;  
}
