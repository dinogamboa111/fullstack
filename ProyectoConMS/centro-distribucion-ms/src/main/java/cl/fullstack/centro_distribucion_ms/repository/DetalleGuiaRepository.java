package cl.fullstack.centro_distribucion_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.fullstack.centro_distribucion_ms.entity.DetalleGuiaEntity;

public interface DetalleGuiaRepository extends JpaRepository<DetalleGuiaEntity, Integer> {
}
