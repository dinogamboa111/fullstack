package cl.fullstack.pruducto_ms.dto;


import lombok.Data;

@Data
public class CategoriaDTO {

    private int idCategoria;
    private String nombreCategoria;
    private String descripcion;
}