package cl.fullstack.pedido_ms.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaDTO {
    private int idComuna;
    private String nombre;
    private int idProvincia; // Relacion con ProvinciaDTO
}
