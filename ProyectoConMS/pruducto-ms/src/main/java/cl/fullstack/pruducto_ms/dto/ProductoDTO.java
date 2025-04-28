package cl.fullstack.pruducto_ms.dto;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private int stock;
    private double precioCompra;
    private double precioVenta;
    private boolean estado;
    private Long idCategoria; // Referencia  categoria, solo buscamos transmiutir una simplificacion de datos
    // sin ir a la entidad o relacion completa de la bbddasi evitamos siobrecarga de datos y usar la dto para lo q 
    //sirve realmente que es representar solo los datos que vamos a transferir, mientras que la logica de la entidad
    // y relaciones se manejan en los service o repository
    private Long idProveedor; // Referencia  proveedor
}