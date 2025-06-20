package cl.fullstack.pedido_ms.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class DetallePedidoKey implements Serializable {

    private int pedidoId;
    private int productoId;



    //ESTE METODO COMPARA SI DOS OBJETOS TIENEN EL MISMO VALOR DE ATRIBUTOS PEDIDOID Y PRODUCTO ID, SI SON IGUALES LOS OBEJTOS SE CONSIDERA IGUALES
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DetallePedidoKey)) return false;
        DetallePedidoKey that = (DetallePedidoKey) o;
        return pedidoId == that.pedidoId && productoId == that.productoId;
    }
     //ESTE METODO GENERA UN NUMERO UNICO BASADO EN LOS ATRIBUTOS DEL OBJETO ANTERIOR LO QUE PERMTE ORGANIZAR Y BUSCAR DE MANERA AMS EFICIENTE POR EL HASH CREADO
    @Override
    public int hashCode() {
        return Objects.hash(pedidoId, productoId);
    }
    //ESTO FUNCIONA COMO SI FUESE DOBLE VERIFICACION, PRIMERO USARA HASH PARA REDUCIR EL ESPACIO DE BUSQUEDA. SI LOS HASG NO SON IGUALES NO TIENE SENTIDO LA OTRA VERIFICACION
    //SI EL HASH ES IGUAL, SE VERIFICA EN PROFFUNDIDAD CON EL EQUALS
}
