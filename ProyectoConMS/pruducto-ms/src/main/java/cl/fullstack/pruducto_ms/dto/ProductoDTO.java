package cl.fullstack.pruducto_ms.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO que representa un producto dentro del sistema")
public class ProductoDTO {

    @Schema(description = "Identificador único del producto", example = "1")
    private int idProducto;

    @Schema(description = "Nombre del producto", example = "Coca-Cola 1.5L")
    private String nombreProducto;

    @Schema(description = "ID del cliente asociado al producto", example = "10")
    private int idCliente;

    @Schema(description = "ID de la categoría a la que pertenece el producto", example = "2")
    private int idCategoria; 
}

    
    
    // Referencia  categoria, solo buscamos transmiutir una simplificacion de datos
    // sin ir a la entidad o relacion completa de la bbddasi evitamos siobrecarga de datos y usar la dto para lo q 
    //sirve realmente que es representar solo los datos que vamos a transferir, mientras que la logica de la entidad
    // y relaciones se manejan en los service o repository
    
