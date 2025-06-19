package cl.fullstack.ubicacion_ms.repository;

import cl.fullstack.ubicacion_ms.entity.RegionEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {

      Optional<RegionEntity> findByNombreIgnoreCase(String nombre);   //ojito
}
