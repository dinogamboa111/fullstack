package cl.fullstack.centro_distribucion_ms.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "guia_despacho")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuiaDespachoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guia_seq")
    @SequenceGenerator(name = "guia_seq", sequenceName = "GUIA_DESPACHO_SEQ", allocationSize = 1)
    @Column(name = "id_guia")
    private int idGuia;

    @Column(name = "id_despachador", nullable = false)
    private int idDespachador;

    // Relación con los detalles (pedidos asociados a la guía)
    @OneToMany(mappedBy = "guiaDespacho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleGuiaEntity> pedidosAsociados;
}
