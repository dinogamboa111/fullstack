package cl.fullstack.pruducto_ms.dto;

import lombok.Data;

@Data
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private String direccion;
}