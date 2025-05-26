package cl.fullstack.centro_distribucion_ms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // genera getters, setters y otros metodos automaticamente
@NoArgsConstructor // genera constructor vacio
@AllArgsConstructor // genera constructor con todos los campos

@Entity
@Table(name = "Comuna_cubierta") // tabla intermedia
@IdClass(ComunaCubiertaPKcompuesta.class)  //llama a clase ComunaCubiertaPKcompuesta , para poder usar @id en mas columnas (clave compuesta)
public class ComunaCubiertaEntity {

 
    @Id
    @Column(name = "id_centro") // id del centro de distribucion, no es PK
    private int idCentro;
    
    @Id
    @Column(name = "id_comuna") // id de la comuna que cubre
    private int idComuna;
}


