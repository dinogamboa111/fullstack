package cl.fullstack.centro_distribucion_ms.dto;

import lombok.Data;
import java.util.List;

@Data
public class GuiaDespachoResponseDTO {
    private Long idGuia;
    private Long idDespachador;
    private List<PedidoAsociadoDTO> pedidosAsociados;
}

