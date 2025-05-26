package cl.fullstack.centro_distribucion_ms.service;

import cl.fullstack.centro_distribucion_ms.dto.CentroDistribucionDTO;
import java.util.List;

public interface ICentroDistribucionService {

    CentroDistribucionDTO crearCentroDistribucion(CentroDistribucionDTO centroDistribucion);

    List<CentroDistribucionDTO> obtenerTodosLosCentros();

    CentroDistribucionDTO obtenerCentroDistribucionPorId(int idCentro); // ← CAMBIADO

    void eliminarCentroDistribucion(int idCentro);
}
