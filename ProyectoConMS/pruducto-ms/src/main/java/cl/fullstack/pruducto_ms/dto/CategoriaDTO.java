package cl.fullstack.pruducto_ms.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoriaDTO {

    private int idCategoria;

    @NotNull(message = "El nombre de la categoria no puede ser nula")
    @Size(min = 3, max = 50, message = "El nombre de la categoria debe tener entre 3 y 50 caracteres")
    private String nombreCategoria;

    @NotNull(message = "La descripcion no puede ser nula")
    @Size(min = 3, max = 50, message = "la descripcion de la categoria debe tener entre 3 y 50 caracteres")
    private String descripcion;
}