package cl.fullstack.pedido_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.fullstack.pedido_ms.entity.DetallePedidoEntity;
import cl.fullstack.pedido_ms.entity.DetallePedidoId;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, DetallePedidoId> {

}
