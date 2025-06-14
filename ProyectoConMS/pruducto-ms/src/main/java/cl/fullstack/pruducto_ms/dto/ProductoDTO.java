package cl.fullstack.pruducto_ms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductoDTO {


    private int idProducto;
    
    @NotNull(message = "El nombre del producto no puede ser nulo")
    @Size(min = 3, max = 50, message = "el nombre del producto debe tener entre 3 y 50 caracteres")
    private String nombreProducto;
   
    //deberia cambiarlos a integer para q esto funcione bien 
    @NotNull(message = "El ID de cliente no puede ser nulo")
    private int idCliente;

    
    @NotNull(message = "El ID de categoria no puede ser nulo")
    private int idCategoria; // Referencia  categoria, solo buscamos transmiutir una simplificacion de datos
    // sin ir a la entidad o relacion completa de la bbddasi evitamos siobrecarga de datos y usar la dto para lo q 
    //sirve realmente que es representar solo los datos que vamos a transferir, mientras que la logica de la entidad
    // y relaciones se manejan en los service o repository
    
}