package cl.fullstack.pedido_ms.dto;

import lombok.Data;

@Data

//esta dto es necesaria porq permite interactar con producto-ms mas adelante hay que modificarla con los atributos que queramos
public class ProductoDTO {
    private int idProducto;
    private String nombreProducto;
    private int idCliente;
    private int idCategoria; 

}