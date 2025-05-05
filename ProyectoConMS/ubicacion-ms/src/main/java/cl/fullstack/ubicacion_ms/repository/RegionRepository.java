package cl.fullstack.ubicacion_ms.repository;

import cl.fullstack.ubicacion_ms.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
}
