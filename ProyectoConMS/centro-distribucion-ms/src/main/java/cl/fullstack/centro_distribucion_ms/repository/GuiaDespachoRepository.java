package cl.fullstack.centro_distribucion_ms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.fullstack.centro_distribucion_ms.entity.GuiaDespachoEntity;

@Repository
public interface GuiaDespachoRepository extends JpaRepository<GuiaDespachoEntity, Integer> {

   
}
