package cl.fullstack.ubicacion_ms.repository;

import cl.fullstack.ubicacion_ms.entity.ComunaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ComunaRepository extends JpaRepository<ComunaEntity, Integer> {
    List<ComunaEntity> findByProvinciaIdProvincia(int idProvincia); // Busca por ID de provincia
}
