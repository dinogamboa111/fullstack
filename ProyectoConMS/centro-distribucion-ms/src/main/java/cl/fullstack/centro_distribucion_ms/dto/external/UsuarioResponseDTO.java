package cl.fullstack.centro_distribucion_ms.dto.external;

import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private int id;
    private String nombre;
    private long idRol;  // o int según definiste
}
