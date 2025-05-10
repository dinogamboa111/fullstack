package cl.fullstack.usuario_ms.dto;


import lombok.Data;

@Data
public class UsuarioDTO {
    
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String rol; // ADMIN o USER
}
