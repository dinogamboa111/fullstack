package cl.fullstack.centro_distribucion_ms.repository;

import cl.fullstack.centro_distribucion_ms.entity.CentroDistribucionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository // indica que esta interfaz es un repositorio de datos
public interface CentroDistribucionRepository extends JpaRepository<CentroDistribucionEntity, Integer> {
    // repositorio para operaciones CRUD sobre centro distribucion

     @Query("SELECT c FROM CentroDistribucionEntity c JOIN c.comunasCubiertas cc WHERE cc.idComuna = :idComuna")
    Optional<CentroDistribucionEntity> findByComunaCubierta(@Param("idComuna") int idComuna);
}
