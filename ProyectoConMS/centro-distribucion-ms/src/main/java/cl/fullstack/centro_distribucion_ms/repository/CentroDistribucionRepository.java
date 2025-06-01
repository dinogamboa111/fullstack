package cl.fullstack.centro_distribucion_ms.repository;

import cl.fullstack.centro_distribucion_ms.entity.CentroDistribucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // indica que esta interfaz es un repositorio de datos
public interface CentroDistribucionRepository extends JpaRepository<CentroDistribucionEntity, Integer> {
    // repositorio para operaciones CRUD sobre centro distribucion
}
