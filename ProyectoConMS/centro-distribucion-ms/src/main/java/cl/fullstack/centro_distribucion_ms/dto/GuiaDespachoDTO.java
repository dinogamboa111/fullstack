package cl.fullstack.centro_distribucion_ms.dto;

import lombok.Data;
import java.util.List;

@Data
public class GuiaDespachoDTO {
    private int idGuia;                     // ID de la guía (opcional en creación, útil en respuesta)
    private int idDespachador;          // ID del despachador
    private List<DetalleGuiaDTO> pedidosAsociados;  // Lista de pedidos relacionados
}
