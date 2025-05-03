package cl.fullstack.cliente_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.fullstack.cliente_ms.entity.ClienteEntity;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer > {

}
