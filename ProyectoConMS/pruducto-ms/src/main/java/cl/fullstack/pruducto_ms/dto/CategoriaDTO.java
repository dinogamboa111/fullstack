package cl.fullstack.pruducto_ms.dto;


import lombok.Data;

@Data
public class CategoriaDTO {
    
    private Long id;
    private String nombre;
    private String descripcion;
}