package cl.fullstack.centro_distribucion_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.fullstack.centro_distribucion_ms.entity.ZonaComLogisticaEntity;

public interface ZonaComLogisticaRepository extends JpaRepository<ZonaComLogisticaEntity, Integer> {
    // extiende jpa repository para hacer operaciones de base de datos
    // usa la entidad zona com logistica y la llave primaria es de tipo integer
    // no se necesita agregar metodos porque jpa ya tiene metodos comunes
}
