package cl.fullstack.ubicacion_ms.repository;

import cl.fullstack.ubicacion_ms.entity.ComunaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComunaRepository extends JpaRepository<ComunaEntity, Integer> {

    List<ComunaEntity> findByProvinciaIdProvincia(int idProvincia);

    Optional<ComunaEntity> findByNombreIgnoreCaseAndProvincia_IdProvincia(String nombre, int idProvincia);

    List<ComunaEntity> findByProvincia_IdProvincia(int idProvincia);
}
