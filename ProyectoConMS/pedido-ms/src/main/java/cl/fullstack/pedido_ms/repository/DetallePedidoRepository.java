package cl.fullstack.pedido_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.fullstack.pedido_ms.entity.DetallePedidoEntity;
import cl.fullstack.pedido_ms.entity.DetallePedidoId;

//usaria key o llave, asi se entiende que es una contruccion de id

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedidoEntity, DetallePedidoId> {

}
