package cl.fullstack.usuario_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.fullstack.usuario_ms.entity.RolEntity;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity, Long> {
    Optional<RolEntity> findByNombre(String nombre);
}