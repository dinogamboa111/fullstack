package cl.fullstack.usuario_ms.dto;


import lombok.Data;


@Data
public class RolDTO {
    private Long id;
    private String nombre;
    private String descripcion;
}