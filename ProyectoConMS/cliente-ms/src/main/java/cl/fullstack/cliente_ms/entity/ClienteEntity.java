package cl.fullstack.cliente_ms.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEntity {

    @Id
    @Column(name = "rut_cliente")
    private int rutCliente;

    @Column(name = "dv_cliente", nullable = false)
    private char dvCliente;

    @Column(name = "nombre_cliente", nullable = false, length = 20)
    private String nombreCliente;

    @Column(name = "ap_paterno_cliente", nullable = false, length = 20)
    private String apPaternoCliente;

    @Column(name = "ap_materno_cliente", nullable = false, length = 20)
    private String apMaternoCliente;
    
    //telefono con 12 caracteres considerando que se puede ingresar +569
    @Column(name = "telefono", nullable = false, length = 12 )
    private String telefono;

    @Column(name = "email", nullable = false, length = 20, unique = true)
    private String email;

    @Column(name = "num_calle", nullable = false, length = 20)
    private String numCalle;

    @Column(name = "nombre_calle", nullable = false, length = 20)
    private String nombreCalle;

    @Column(name = "id_comuna", nullable = false)
    private int idComuna; // FK no es necesaria declararla ya que viene de otro microservicio
}
