package cl.fullstack.pruducto_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private int rutCliente;      
    private char dvCliente;    
    private String nombreCliente;
    private String apPaternoCliente;
    private String apMaternoCliente;
    private String telefono; 
    private String email;
    private String numCalle;
    private String nombreCalle;
    private int idComuna;

}