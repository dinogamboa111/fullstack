package cl.fullstack.ubicacion_ms.repository;

import cl.fullstack.ubicacion_ms.entity.ComunaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ComunaRepository extends JpaRepository<ComunaEntity, Integer> {

    List<ComunaEntity> findByProvinciaIdProvincia(int idProvincia); // Busca por ID de provincia

    // Para validar duplicados ignorando mayusculas dentro de una provincia
    Optional<ComunaEntity> findByNombreIgnoreCaseAndProvinciaIdProvincia(String nombre, int idProvincia);
=======
    List<ComunaEntity> findByIdProvincia_IdProvincia(int idProvincia); // Busca por ID de provincia

}
