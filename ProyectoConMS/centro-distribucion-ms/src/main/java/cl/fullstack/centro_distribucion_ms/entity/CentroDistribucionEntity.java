package cl.fullstack.centro_distribucion_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data // genera getters, setters y otros metodos automaticamente
@NoArgsConstructor // genera constructor vacio
@AllArgsConstructor // genera constructor con todos los campos

@Entity // indica que esta clase es una entidad para la base de datos
@Table(name = "Centro_Distribucion") // define el nombre de la tabla en la base

public class CentroDistribucionEntity {

    @Id // define que este campo es la llave primaria
    @Column(name = "id_centro") // define el id del centro en la tabla
    private int idCentro; // id del centro

    @Column(name = "nombre_centro", nullable = false) // columna nombre_centro no puede ser nula
    private String nombreCentro; // nombre del centro

    @Column(name = "direccion", nullable = false) // columna direccion no puede ser nula
    private String direccion; // direccion del centro

    @Column(name = "id_comuna") // define el id de comuna del centro (sede principal)
    private int idComuna; // comuna base del centro

    @Column(name = "nombre_comuna", nullable = false) // columna nombre_comuna no puede ser nula
    private String nombreComuna; // nombre de la comuna base

    @Column(name = "nombre_provincia", nullable = false) // columna nombre_provincia no puede ser nula
    private String nombreProvincia; // nombre de la provincia base

    @Column(name = "nombre_region", nullable = false) // columna nombre_region no puede ser nula
    private String nombreRegion; // nombre de la region base


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // relacion uno a muchos: un centro puede tener muchas comunas cubiertas
                                                                  // fetch LAZY: las comunas cubiertas se cargan solo cuando se acceden (mejora rendimiento)
                                                                  // cascade ALL: operaciones (guardar, borrar, actualizar) en centro se aplican tambien a comunas cubiertas


    @JoinColumn(name = "id_centro", referencedColumnName = "id_centro") // referencia la columna id_centro en comuna_cubierta
    private List<ComunaCubiertaEntity> comunasCubiertas; // lista de comunas que cubre este centro
}

