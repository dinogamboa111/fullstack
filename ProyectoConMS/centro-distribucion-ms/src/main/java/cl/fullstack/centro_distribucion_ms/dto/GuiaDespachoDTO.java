package cl.fullstack.centro_distribucion_ms.dto;

import lombok.Data;
import java.util.List;

@Data
public class GuiaDespachoDTO {
    private Long idGuia;                     // ID de la guía (opcional en creación, útil en respuesta)
    private Integer idDespachador;          // ID del despachador
    private List<DetalleGuiaDTO> pedidosAsociados;  // Lista de pedidos relacionados
}
