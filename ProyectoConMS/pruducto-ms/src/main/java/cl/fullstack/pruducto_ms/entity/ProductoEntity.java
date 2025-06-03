package cl.fullstack.pruducto_ms.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "producto")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "producto_seq")
    @SequenceGenerator(name = "producto_seq", sequenceName = "PRODUCTO_SEQ", allocationSize = 1)
    @Column(name = "id_producto")
    private int idProducto;

    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;


    @Column(name = "id_cliente", nullable=false)
    private int idCliente;

   // @ManyToOne
    //@JoinColumn(name = "id_categoria")
    //private CategoriaEntity idCategoria;
@Column(name = "id_categoria", nullable=false)
    private int idCategoria;
 
}


