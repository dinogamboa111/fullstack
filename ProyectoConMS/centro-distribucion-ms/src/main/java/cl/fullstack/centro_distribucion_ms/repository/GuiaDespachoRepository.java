
package cl.fullstack.centro_distribucion_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.fullstack.centro_distribucion_ms.entity.GuiaDespachoEntity;

public interface GuiaDespachoRepository extends JpaRepository<GuiaDespachoEntity, Integer> {
}
