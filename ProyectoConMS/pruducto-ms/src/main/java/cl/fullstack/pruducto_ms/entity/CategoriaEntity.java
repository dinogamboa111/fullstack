package cl.fullstack.pruducto_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categoria")
public class CategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categoria_seq")
    //sirve para generar desde aca la seq de numeracion automatica, pasa que oracle no soporta generation type solo
    @SequenceGenerator(name = "categoria_seq", sequenceName = "CATEGORIA_SEQ", allocationSize = 1)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;
}