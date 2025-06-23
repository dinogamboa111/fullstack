package cl.fullstack.centro_distribucion_ms.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

//con esto anotamos la clase como una entidad jpa que mapeara la tabla a la bbdd
@Entity
@Table(name = "guia_despacho")
@Data
public class GuiaDespachoEntity {

     //definimos la clave primaria de la entidad
    @Id
    //secuencia que usaremos, le dice a jpa que el valor del id se generara automaticamente
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guia_seq")
    //define el generador de secuencia que usaremos: guia_seq
    @SequenceGenerator(name = "guia_seq", sequenceName = "GUIA_DESPACHO_SEQ", allocationSize = 1)
    @Column(name = "id_guia")
    private int idGuia;

    @Column(name = "id_despachador")
    private int idDespachador;
    //relacion uno  amuchos, una guia muchos detalles
    //mappedby indica que la relacion es bidireccional y q la otra propiedad esta en DetalleGuiaEntity
    //cascade ahce que todo lo que se haga aqui, como eliminar por ej, se haga en el detalle asociado
    //orphanRemoval, elimina automaticamente los detalles huerfanos si se eliman de la lista 
    @OneToMany(mappedBy = "guiaDespacho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleGuiaEntity> detalles = new ArrayList<>();

}
