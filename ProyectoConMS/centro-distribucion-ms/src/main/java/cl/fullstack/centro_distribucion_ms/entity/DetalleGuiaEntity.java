package cl.fullstack.centro_distribucion_ms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "detalle_guia")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleGuiaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detalle_guia_seq")
    @SequenceGenerator(name = "detalle_guia_seq", sequenceName = "DETALLE_GUIA_SEQ", allocationSize = 1)
    @Column(name = "id_detalle")
    private int idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_guia", nullable = false)
    private GuiaDespachoEntity guiaDespacho;

    @Column(name = "id_pedido", nullable = false)
    private int idPedido; // ID del pedido proveniente de pedido-ms
}
