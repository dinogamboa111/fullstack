package cl.fullstack.centro_distribucion_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // genera getters, setters y otros metodos automaticamente
@NoArgsConstructor // genera constructor vacio
@AllArgsConstructor // genera constructor con todos los campos

@Entity // indica que esta clase es una entidad para la base de datos
@Table(name = "Zona_Logistica_Comuna") // define el nombre de la tabla en la base

public class ZonaComLogisticaEntity {
    @Id // define que este campo es la llave primaria
    @Column(name = "id_comuna") // define el nombre de la columna en la tabla
    private int idComuna; // id de la comuna

    @Column(name = "nombre_comuna", nullable = false) // columna nombre_comuna no puede ser nula
    private String nombreComuna; // nombre de la comuna

    @Column(name = "codigo_zona", nullable = false) // columna codigo_zona no puede ser nula
    private String codigoZona; // codigo de la zona

    @Column(name = "nombre_zona", nullable = false) // columna nombre_zona no puede ser nula
    private String nombreZona; // nombre de la zona
}

