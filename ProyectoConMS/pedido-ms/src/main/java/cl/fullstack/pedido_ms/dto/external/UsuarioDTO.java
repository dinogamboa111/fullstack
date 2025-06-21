package cl.fullstack.pedido_ms.dto.external;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String nombre;
    private String email;
    private String password;
    private Integer idComuna;
    private RolDTO rol;
    private Integer idCentro;

    @Data
    public static class RolDTO {
        private Integer id;
        private String nombre;
        private String descripcion;
    }
}
