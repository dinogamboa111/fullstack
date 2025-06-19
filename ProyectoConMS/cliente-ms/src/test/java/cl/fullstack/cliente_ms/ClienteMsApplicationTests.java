package cl.fullstack.cliente_ms;

import cl.fullstack.cliente_ms.dto.ClienteDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
// Activa el logger SLF4J para usar log.info, log.debug, etc.

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// Indica que el test arrancará el servidor Spring Boot en un puerto fijo (no
// aleatorio),
// definido en la configuración (ej. 8910).

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Define que el orden de ejecución de los tests será el indicado por la
// anotación @Order en cada método.

public class ClienteMsApplicationTests {

    private final int port = 8910;
    // Puerto fijo donde el servidor se espera que esté corriendo (debe coincidir
    // con la configuración real).

    @Autowired
    private TestRestTemplate restTemplate;
    // Cliente HTTP que permite hacer peticiones REST al servidor arrancado en el
    // test.

    private static final int TEST_RUT = 11222333;
    // Constante con un valor fijo para el RUT del cliente de prueba.

    private String getUrl(String path) {
        return "http://localhost:" + port + "/api/clientes" + path;
        // Método auxiliar que construye la URL completa para llamar al endpoint REST
        // del microservicio cliente.
    }

    @Test
    @Order(1)
    // Primer test que se ejecutará según el orden definido.

    void testCrearCliente() {
        ClienteDTO cliente = new ClienteDTO();
        // Crea un objeto DTO para representar el cliente a enviar en la petición POST.

        cliente.setRutCliente(TEST_RUT);
        cliente.setDvCliente('9');
        cliente.setNombreCliente("Juan");
        cliente.setApPaternoCliente("Tester");
        cliente.setApMaternoCliente("Integrador");
        cliente.setTelefono("987654321");
        cliente.setEmail("juan.integrador@prueba.cl");
        cliente.setNumCalle("123");
        cliente.setNombreCalle("Av. Test");
        cliente.setIdComuna(1);
        // Se establecen los datos del cliente, que serán enviados al backend.
        // El ID de comuna debe existir en la base para que la creación sea válida.

        ResponseEntity<ClienteDTO> response = restTemplate.postForEntity(
                getUrl(""), // URL base: http://localhost:8910/api/clientes
                cliente, // Objeto cliente que será enviado como cuerpo de la petición
                ClienteDTO.class // Clase esperada en la respuesta para mapear el JSON recibido
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        // Valida que el servidor respondió con HTTP 201 Created, lo que indica éxito al
        // crear.

        ClienteDTO clienteResponse = response.getBody();
        // Obtiene el cuerpo de la respuesta, que es un ClienteDTO (puede ser null).

        assertThat(clienteResponse).isNotNull();
        // Verifica que el cuerpo de la respuesta no sea null.

        assertThat(clienteResponse.getRutCliente()).isEqualTo(TEST_RUT);
        // Verifica que el cliente creado tenga el mismo RUT que el enviado.
    }

    @Test
    @Order(2)
    // Segundo test que se ejecuta: obtener cliente por RUT.

    void testObtenerCliente() {

        System.out.println("Test empieza");
        ResponseEntity<ClienteDTO> response = restTemplate.getForEntity(
                getUrl("/" + TEST_RUT),
                ClienteDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        ClienteDTO clienteResponse = response.getBody();

        assertThat(clienteResponse).isNotNull();

        log.info("Cliente obtenido: {}", clienteResponse);
        // Esto imprime el cliente obtenido usando el logger SLF4J.

        assertThat(clienteResponse.getNombreCliente()).isEqualTo("Juan");
    }

    @Test
    @Order(3)
    // Tercer test que elimina el cliente creado.

    void testEliminarCliente() {

        System.out.println("Test empieza");

        restTemplate.delete(getUrl("/" + TEST_RUT));
        // Hace una petición DELETE a /api/clientes/11222333 para eliminar el cliente.

        ResponseEntity<String> response = restTemplate.getForEntity(
                getUrl("/" + TEST_RUT), // Luego intenta obtener el cliente eliminado
                String.class // Espera respuesta tipo String (error o mensaje)
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        // Verifica que ahora la respuesta sea HTTP 404 Not Found,
        // confirmando que el cliente ya no existe.
    }
}
