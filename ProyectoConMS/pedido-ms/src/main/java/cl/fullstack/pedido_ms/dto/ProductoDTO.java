package cl.fullstack.pedido_ms.dto;

import lombok.Data;

@Data

//esta dto es necesaria porq permite interactar con producto-ms mas adelante hay que modificarla con los atributos que queramos
public class ProductoDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private int stock;
    private double precioCompra;
    private double precioVenta;
    private boolean estado;
    private int idCategoria; 
    private int idProveedor; 
}