package cl.fullstack.pruducto_ms.dto;

import lombok.Data;

@Data
public class ProveedorDTO {
    private Long id;
    private String nombre;
    private String telefono;
    private String numCalle;
    private String nombreCalle;
    private int idComuna;
}