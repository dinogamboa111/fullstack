package cl.fullstack.centro_distribucion_ms.service;

import cl.fullstack.centro_distribucion_ms.dto.CentroDistribucionDTO;
import cl.fullstack.centro_distribucion_ms.entity.CentroDistribucionEntity;

import java.util.List;
import java.util.Optional;

public interface ICentroDistribucionService {

    CentroDistribucionDTO crearCentroDistribucion(CentroDistribucionDTO centroDistribucion);

    List<CentroDistribucionDTO> obtenerTodosLosCentros();

    CentroDistribucionDTO obtenerCentroDistribucionPorId(int idCentro); // ← CAMBIADO

    Optional<CentroDistribucionEntity> findByComunaCubierta(int idComuna);

    void eliminarCentroDistribucion(int idCentro);
}
