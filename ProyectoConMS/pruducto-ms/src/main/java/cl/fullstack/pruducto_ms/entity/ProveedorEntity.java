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
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;
    
    //12 caracteres en telefono por si ingresa +569
    @Column(name = "telefono", nullable = false, length = 12)
    private String telefono;

     @Column(name = "num_calle", nullable = false, length = 20)
    private String numCalle;

    @Column(name = "nombre_calle", nullable = false, length = 20)
    private String nombreCalle;

    @Column(name = "id_comuna", nullable = false)
    private int idComuna;

}
