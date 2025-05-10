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
    @Column(name = "id")
    private int id;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

     @Column(name = "descripcion", nullable = false, length = 20)
    private String descripcion;

     @Column(name = "stock", nullable = false)
    private int stock;

     @Column(name = "precio_compra", nullable = false)
    private double precioCompra;

     @Column(name = "precio_venta", nullable = false)
    private double precioVenta;

     @Column(name = "estado", nullable = false)
    private boolean estado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaEntity categoria;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_proveedor", nullable = false)
    private ProveedorEntity proveedor;
}