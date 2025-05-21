package cl.fullstack.centro_distribucion_ms.service;

import cl.fullstack.centro_distribucion_ms.dto.ZonaComLogisticaDTO;
import java.util.List;

public interface IZonaComLogisticaService {
    
    // recibe un dto de zona, lo guarda y devuelve el dto guardado
    ZonaComLogisticaDTO crearZonaComLogistica(ZonaComLogisticaDTO zonacomlogistica);
    
    // devuelve la lista completa de zonas guardadas
    List<ZonaComLogisticaDTO> obtenerZonaComLogistica();
    
    // recibe un id de zona y devuelve el dto correspondiente
    ZonaComLogisticaDTO obtenerZonaComLogisticaPorId(int idZonaComLogistica);
    
    // recibe un id y elimina la zona correspondiente
    void eliminarZonaComLogistica(int idZonaComLogistica);
}

