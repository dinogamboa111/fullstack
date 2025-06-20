package cl.fullstack.ubicacion_ms.service;

import cl.fullstack.ubicacion_ms.dto.RegionDTO;
import java.util.List;

public interface IRegionService {
    RegionDTO crearRegion(RegionDTO region);
    List<RegionDTO> obtenerRegiones();
    RegionDTO obtenerRegionPorId(int idRegion);
    void eliminarRegion(int idRegion);

    RegionDTO actualizarRegion(int idRegion, RegionDTO regionDTO);
}
