package cl.fullstack.usuario_ms.dto;


import lombok.Data;

@Data
public class UsuarioDTO {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private int idComuna;
    private RolDTO rol; 
    private Integer idCentro;
}
