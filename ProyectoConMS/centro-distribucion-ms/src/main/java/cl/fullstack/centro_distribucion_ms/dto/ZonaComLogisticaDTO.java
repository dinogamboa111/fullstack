package cl.fullstack.centro_distribucion_ms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // genera getters, setters y otros metodos automaticamente
@NoArgsConstructor // genera constructor vacio
@AllArgsConstructor // genera constructor con todos los campos

public class ZonaComLogisticaDTO {

    @NotNull(message = "el identificador de comuna no puede ser nulo")
    private int idComuna; // id de la comuna, no puede ser nulo

    @NotNull(message = "el nombre de comuna no puede ser nulo")
    private String nombreComuna; // nombre de la comuna, no puede ser nulo

    @NotNull(message = "el codigo de zona para logistica no puede ser nulo")
    private String codigoZona; // codigo de la zona para logistica, no puede ser nulo

    @NotNull(message = "el nombre para de zona para logistica no puede ser nulo")
    private String nombreZona; // nombre de la zona para logistica, no puede ser nulo
}

