package cl.fullstack.ubicacion_ms.repository;

import cl.fullstack.ubicacion_ms.entity.ProvinciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, Integer> {
    List<ProvinciaEntity> findByRegionIdRegion(int idRegion); // Busca por ID de region
}
