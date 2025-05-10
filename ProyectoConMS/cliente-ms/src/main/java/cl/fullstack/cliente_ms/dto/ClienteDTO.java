package cl.fullstack.cliente_ms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    @NotNull(message = "El RUT no puede ser nulo")
    @Min(value = 100000, message = "El RUT debe tener al menos  dígitos")  // Puedes definir un valor mínimo
    @Max(value = 99999999, message = "El RUT no puede tener más de 8 dígitos")
    private int rutCliente;  // RUT como tipo int


    @NotNull(message = "El dígito verificador no puede ser nulo")
    private char dvCliente;  // Aquí representas el dígito verificador

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    private String nombreCliente;

    @NotNull(message = "El apellido paterno no puede ser nulo")
    @Size(min = 3, max = 50, message = "El apellido paterno debe tener entre 3 y 50 caracteres")
    private String apPaternoCliente;

    @NotNull(message = "El apellido materno no puede ser nulo")
    @Size(min = 3, max = 50, message = "El apellido materno debe tener entre 3 y 50 caracteres")
    private String apMaternoCliente;

    @NotNull(message = "El teléfono no puede ser nulo")
    private String telefono;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El correo electrónico debe tener un formato válido")
    private String email;

    @NotNull(message = "El número de calle no puede ser nulo")
    private String numCalle;

    @NotNull(message = "El nombre de la calle no puede ser nulo")
    private String nombreCalle;

    @NotNull(message = "La comuna no puede ser nula")
    private int idComuna;


}
