package cl.fullstack.ubicacion_ms.repository;

import cl.fullstack.ubicacion_ms.entity.ProvinciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProvinciaRepository extends JpaRepository<ProvinciaEntity, Integer> {

    List<ProvinciaEntity> findByRegionIdRegion(int idRegion); // Busca por ID de region

    // Evita duplicados por nombre (case-insensitive) dentro de una misma region
    Optional<ProvinciaEntity> findByNombreIgnoreCaseAndRegionIdRegion(String nombre, int idRegion);

    List<ProvinciaEntity> findByIdRegion_IdRegion(int idRegion); // Busca por ID de region

}
