package cl.fullstack.pedido_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.fullstack.pedido_ms.entity.PedidoEntity;

@Repository
public interface PedidoRepository extends JpaRepository <PedidoEntity, Integer>{

}


