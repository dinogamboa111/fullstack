package cl.fullstack.cliente_ms.response;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class ClienteResponse {

    
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotNull(message = "El RUT no puede ser nulo")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]{1}$", message = "El formato del RUT es inválido")
    private String rut;

    @Email(message = "El correo electrónico debe tener un formato válido")
    private String correo;

    @NotNull(message = "La dirección no puede ser nula")
    @Size(min = 10, max = 100, message = "La dirección debe tener entre 10 y 100 caracteres")
    private String direccion;
}