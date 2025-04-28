package cl.fullstack.pruducto_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "proveedor")
public class ProveedorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "proveedor_seq")

    //sirve para generar desde aca la seq de numeracion automatica, pasa que oracle no soporta generation type solo
    @SequenceGenerator(name = "proveedor_seq", sequenceName = "PROVEEDOR_SEQ", allocationSize = 1)
    private Long id;

    private String nombre;
    private String telefono;
    private String direccion;
}
