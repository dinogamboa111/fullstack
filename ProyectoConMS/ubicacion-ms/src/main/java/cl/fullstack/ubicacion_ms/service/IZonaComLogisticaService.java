package cl.fullstack.ubicacion_ms.service;

import cl.fullstack.ubicacion_ms.dto.ZonaComLogisticaDTO;
import java.util.List;

public interface IZonaComLogisticaService {
    ZonaComLogisticaDTO crearZonaComLogistica(ZonaComLogisticaDTO zonacomlogistica);
    List<ZonaComLogisticaDTO> obtenerZonaComLogistica();
    ZonaComLogisticaDTO obtenerZonaComLogisticaPorId(int idZonaComLogistica);
    void eliminarZonaComLogistica(int idZonaComLogistica);
}

