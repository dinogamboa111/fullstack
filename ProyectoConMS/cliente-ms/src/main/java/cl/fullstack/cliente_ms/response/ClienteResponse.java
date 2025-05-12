package cl.fullstack.cliente_ms.response;






import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class ClienteResponse {

    
   
    private String nombre;

    
    private int rut;

   
    private String correo;


    private String direccion;
}