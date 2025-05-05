package cl.fullstack.pedido_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.fullstack.pedido_ms.entity.TipoMovimientoEntity;

@Repository
public interface TipoMovimientoRepository extends JpaRepository<TipoMovimientoEntity, Integer> {

}
