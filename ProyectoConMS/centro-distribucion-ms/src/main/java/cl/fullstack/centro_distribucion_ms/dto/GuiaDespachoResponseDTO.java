package cl.fullstack.centro_distribucion_ms.dto;

import lombok.Data;
import java.util.List;

import cl.fullstack.centro_distribucion_ms.dto.external.PedidoDTO;

@Data
public class GuiaDespachoResponseDTO {
    private int idGuia;
    private int idDespachador;
    private List<PedidoDTO> pedidosAsociados;
}
