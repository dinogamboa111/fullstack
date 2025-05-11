package cl.fullstack.cliente_ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.fullstack.cliente_ms.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {

    Optional<ClienteEntity> findByRutCliente(int rutCliente);

    Optional<ClienteEntity> findByEmail(String email);

    // boolean existsByEmail(String email);

    // boolean existsByRutCliente(int rutCliente);

    @Query("SELECT count(c) > 0 FROM ClienteEntity c WHERE c.rutCliente = :rut")
    boolean verificarExistenciaPorRut(@Param("rut") int rut);

}