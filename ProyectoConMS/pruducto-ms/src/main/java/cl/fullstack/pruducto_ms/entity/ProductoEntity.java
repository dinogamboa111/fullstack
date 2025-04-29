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
    private Long id;

    private String nombre;
    private String descripcion;
    private int stock;
    private double precioCompra;
    private double precioVenta;
    private boolean estado;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    private ProveedorEntity proveedor;
}