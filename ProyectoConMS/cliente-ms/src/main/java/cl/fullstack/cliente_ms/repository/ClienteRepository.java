package cl.fullstack.cliente_ms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import cl.fullstack.cliente_ms.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {


    Optional<ClienteEntity> findByRutCliente(int rutCliente);

    Optional<ClienteEntity> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRutCliente(int rutCliente);

}