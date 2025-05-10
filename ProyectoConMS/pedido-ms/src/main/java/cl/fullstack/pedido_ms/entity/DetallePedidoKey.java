package cl.fullstack.pedido_ms.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//serializable es una interfaz, jpa necesita que la clave compuesta implemente serializable para ser guardada o movida dentro de dif. contextos
//es un requisito tecnico de JPA

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DetallePedidoKey implements Serializable {

    private int pedidoId;
    private int productoId;



    //Compara si dos objetos DetallePedidoKey tienen los mismos valores, para que no hayan DetallepedidoKEy duplicadas.
    @Override
    public boolean equals(Object o) {
        //Si el objeto es exactamente el mismo en memoria, no hace falta comparar más.
        if (this == o) return true;
        //Si el otro objeto no es del mismo tipo (DetallePedidoKey), entonces no puede ser igual.
        if (!(o instanceof DetallePedidoKey)) return false;
        //Convertimos (cast) el objeto o al tipo DetallePedidoKey para poder comparar sus campos.
        DetallePedidoKey that = (DetallePedidoKey) o;
        //Compara campo por campo. Solo si ambos valores coinciden, los objetos se consideran iguales.
        return pedidoId == that.pedidoId &&
               productoId == that.productoId;
    }

    //El método hashCode() debe devolver un número entero (int) que representa de manera única un objeto dentro de una colección.
    // en este caso hashCode() calcula un valor de hash basado en los dos campos que forman la clave compuesta: pedidoId y productoId.
    //aqui se garantiza que si dos objetos tienen el mismo valor, en el equals, deben tener tb el mismo hash 
    //es como una doble verificaicon
    @Override
    public int hashCode() {
        return Objects.hash(pedidoId, productoId);
    }
}
