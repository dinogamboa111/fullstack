package cl.fullstack.ubicacion_ms.service;

import cl.fullstack.ubicacion_ms.dto.ProvinciaDTO;
import java.util.List;

public interface IProvinciaService {
    ProvinciaDTO crearProvincia(ProvinciaDTO provincia);
    List<ProvinciaDTO> obtenerProvincias();
    List<ProvinciaDTO> obtenerProvinciasPorRegion(int idRegion);
    ProvinciaDTO obtenerProvinciaPorId(int idProvincia);
    void eliminarProvincia(int idProvincia);

    ProvinciaDTO actualizarProvincia(int idProvincia, ProvinciaDTO provinciaDTO); 
}