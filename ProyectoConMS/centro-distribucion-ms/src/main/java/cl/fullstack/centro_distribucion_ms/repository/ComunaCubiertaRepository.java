package cl.fullstack.centro_distribucion_ms.repository;

import cl.fullstack.centro_distribucion_ms.entity.ComunaCubiertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // indica que esta interfaz es un repositorio de datos
public interface ComunaCubiertaRepository extends JpaRepository<ComunaCubiertaEntity, Long> {
    // repositorio para operaciones CRUD sobre comuna cubierta
}

