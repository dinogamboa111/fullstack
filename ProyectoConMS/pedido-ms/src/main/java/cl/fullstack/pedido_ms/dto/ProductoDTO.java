package cl.fullstack.pedido_ms.dto;


import lombok.Data;

@Data
public class ProductoDTO {
    private int idProducto;
    private String nombre;
    private double precio;
    // Agrega más campos si el endpoint del producto devuelve más información
}


