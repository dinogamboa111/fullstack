package cl.fullstack.cliente_ms; 

// Importaciones necesarias para el test del controlador
import cl.fullstack.cliente_ms.controller.ClienteController; // Importa el controlador a testear
import cl.fullstack.cliente_ms.dto.ClienteDTO; // Importa el DTO usado en los tests
import cl.fullstack.cliente_ms.exception.CorreoDuplicadoException; // Importa excepción personalizada
import cl.fullstack.cliente_ms.exception.DatosInvalidosException; // Importa excepción personalizada
import cl.fullstack.cliente_ms.exception.RecursoDuplicadoException; // Importa excepción personalizada
import cl.fullstack.cliente_ms.exception.RecursoNoEncontradoException; // Importa excepción personalizada
import cl.fullstack.cliente_ms.service.IClienteService; // Importa la interfaz del servicio a mockear
import com.fasterxml.jackson.databind.ObjectMapper; // Utilizado para convertir objetos a JSON
import org.junit.jupiter.api.Test; // Anotación para indicar un test
import org.mockito.Mockito; // Utilidad para simular comportamiento
import org.springframework.beans.factory.annotation.Autowired; // Inyección de dependencias
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest; // Anotación para testear solo el controlador
import org.springframework.boot.test.mock.mockito.MockBean; // Crea mocks del servicio usado por el controlador
import org.springframework.http.MediaType; // Especifica el tipo de contenido
import org.springframework.test.web.servlet.MockMvc; // Utilizado para simular peticiones HTTP
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders; // Builder de requests simuladas
import java.util.List; // Para usar listas

// Imports estáticos para simplificar uso de asserts en la respuesta
import static org.hamcrest.Matchers.is; // Para validar propiedades específicas del JSON
import static org.mockito.ArgumentMatchers.any; // Para hacer match con cualquier argumento
import static org.mockito.ArgumentMatchers.eq; // Para hacer match con un valor específico
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // Validar respuestas

// Anotación que indica que se testea el controlador ClienteController
@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mvc; // Inyección de MockMvc para simular llamadas HTTP

    @MockBean
    private IClienteService clienteService; // Se simula el servicio para controlar sus respuestas

    private final ObjectMapper mapper = new ObjectMapper(); // Usado para convertir objetos a JSON

    private final String BASE_URL = "/api/clientes"; // URL base para las peticiones al controlador

    // Método auxiliar para generar un cliente válido de prueba
    private ClienteDTO getClienteValido() {
        return new ClienteDTO(12345678, 'K', "Juan", "Pérez", "Gonzalez", "912345678", "juan@example.com", "123", "Av. Siempre Viva", 1);
    }

    // Test para creación de cliente con respuesta exitosa (201 Created)
    @Test
    public void crearClienteOk() throws Exception {
        var request = getClienteValido(); // Se crea cliente de prueba
        Mockito.when(clienteService.crearCliente(any())).thenReturn(request); // Se simula respuesta del servicio

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL) // Simula POST a /api/clientes
                        .contentType(MediaType.APPLICATION_JSON) // Define tipo de contenido
                        .content(mapper.writeValueAsString(request))) // Convierte objeto a JSON
                .andExpect(status().isCreated()) // Se espera código 201
                .andExpect(jsonPath("$.rutCliente", is(request.getRutCliente()))); // Se valida campo rutCliente
    }

    // Test para error de RUT duplicado
    @Test
    public void crearClienteRutDuplicado() throws Exception {
        var request = getClienteValido(); // Cliente de prueba
        Mockito.when(clienteService.crearCliente(any())).thenThrow(new RecursoDuplicadoException("RUT duplicado")); // Simula excepción

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isConflict()) // Espera HTTP 409
                .andExpect(jsonPath("$.mensaje", is("RUT duplicado"))); // Valida mensaje de error
    }

    // Test para error por correo duplicado
    @Test
    public void crearClienteCorreoDuplicado() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.crearCliente(any())).thenThrow(new CorreoDuplicadoException("Correo duplicado"));

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // HTTP 400
                .andExpect(jsonPath("$.mensaje", is("Correo duplicado")));
    }

    // Test para error por datos inválidos
    @Test
    public void crearClienteDatosInvalidos() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.crearCliente(any())).thenThrow(new DatosInvalidosException("Datos inválidos"));

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // HTTP 400
                .andExpect(jsonPath("$.mensaje", is("Datos inválidos")));
    }

    // Test para obtener cliente correctamente
    @Test
    public void obtenerClienteOk() throws Exception {
        var cliente = getClienteValido();
        Mockito.when(clienteService.obtenerCliente(12345678)).thenReturn(cliente); // Simula retorno del cliente

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678")) // GET con RUT
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$.nombreCliente", is("Juan"))); // Valida nombre
    }

    // Test cuando no se encuentra el cliente
    @Test
    public void obtenerClienteNoEncontrado() throws Exception {
        Mockito.when(clienteService.obtenerCliente(12345678)).thenThrow(new RecursoNoEncontradoException("No encontrado"));

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
                .andExpect(status().isNotFound()) // HTTP 404
                .andExpect(jsonPath("$.mensaje", is("No encontrado")));
    }

    // Test para listar clientes correctamente
    @Test
    public void listarClientesOk() throws Exception {
        var cliente = getClienteValido();
        Mockito.when(clienteService.listarClientes()).thenReturn(List.of(cliente)); // Simula lista con un cliente

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL)) // GET sin ID
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$.length()", is(1))) // Lista de 1 elemento
                .andExpect(jsonPath("$[0].rutCliente", is(12345678))); // Valida RUT del cliente
    }

    // Test para actualizar cliente correctamente
    @Test
    public void actualizarClienteOk() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenReturn(request); // Simula actualización exitosa

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()) // HTTP 200
                .andExpect(jsonPath("$.email", is("juan@example.com"))); // Valida campo email
    }

    // Test para actualizar cliente con correo ya en uso
    @Test
    public void actualizarClienteCorreoDuplicado() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenThrow(new CorreoDuplicadoException("Correo en uso"));

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest()) // HTTP 400
                .andExpect(jsonPath("$.mensaje", is("Correo en uso")));
    }

    // Test para actualizar cliente que no existe
    @Test
    public void actualizarClienteNoEncontrado() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenThrow(new RecursoNoEncontradoException("Cliente no existe"));

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound()) // HTTP 404
                .andExpect(jsonPath("$.mensaje", is("Cliente no existe")));
    }

    // Test para eliminar cliente exitosamente
    @Test
    public void eliminarClienteOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678")) // DELETE
                .andExpect(status().isNoContent()); // HTTP 204 sin contenido
    }

    // Test para intentar eliminar cliente no existente
    @Test
    public void eliminarClienteNoEncontrado() throws Exception {
        Mockito.doThrow(new RecursoNoEncontradoException("No encontrado")).when(clienteService).eliminarCliente(12345678);

        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
                .andExpect(status().isNotFound()) // HTTP 404
                .andExpect(jsonPath("$.mensaje", is("No encontrado")));
    }
}


// package cl.fullstack.cliente_ms;


// import cl.fullstack.cliente_ms.controller.ClienteController;
// import cl.fullstack.cliente_ms.dto.ClienteDTO;
// import cl.fullstack.cliente_ms.exception.CorreoDuplicadoException;
// import cl.fullstack.cliente_ms.exception.DatosInvalidosException;
// import cl.fullstack.cliente_ms.exception.RecursoDuplicadoException;
// import cl.fullstack.cliente_ms.exception.RecursoNoEncontradoException;
// import cl.fullstack.cliente_ms.service.IClienteService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import java.util.List;
// import static org.hamcrest.Matchers.is;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(ClienteController.class)
// public class ClienteControllerTest {

//     @Autowired
//     private MockMvc mvc;

   
//     @MockBean
//     private IClienteService clienteService;

//     private final ObjectMapper mapper = new ObjectMapper();
//     private final String BASE_URL = "/api/clientes";

//     private ClienteDTO getClienteValido() {
//         return new ClienteDTO(12345678, 'K', "Juan", "Pérez", "Gonzalez", "912345678", "juan@example.com", "123", "Av. Siempre Viva", 1);
//     }
//     //TEST PARA METODO CREAR, CON CASOS DE ERROR
//     @Test
//     public void crearClienteOk() throws Exception {
//         var request = getClienteValido();
//         Mockito.when(clienteService.crearCliente(any())).thenReturn(request);

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(mapper.writeValueAsString(request)))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.rutCliente", is(request.getRutCliente())));
//     }

//     @Test
//     public void crearClienteRutDuplicado() throws Exception {
//         var request = getClienteValido();
//         Mockito.when(clienteService.crearCliente(any())).thenThrow(new RecursoDuplicadoException("RUT duplicado"));

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(mapper.writeValueAsString(request)))
//                 .andExpect(status().isConflict())
//                 .andExpect(jsonPath("$.mensaje", is("RUT duplicado")));
//     }

//     @Test
//     public void crearClienteCorreoDuplicado() throws Exception {
//         var request = getClienteValido();
//         Mockito.when(clienteService.crearCliente(any())).thenThrow(new CorreoDuplicadoException("Correo duplicado"));

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(mapper.writeValueAsString(request)))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.mensaje", is("Correo duplicado")));
//     }

//     @Test
//     public void crearClienteDatosInvalidos() throws Exception {
//         var request = getClienteValido();
//         Mockito.when(clienteService.crearCliente(any())).thenThrow(new DatosInvalidosException("Datos inválidos"));

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(mapper.writeValueAsString(request)))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.mensaje", is("Datos inválidos")));
//     }
//     //TEST PARA METODO OBTENER CLIENTE CON CASOS DE ERROR
//     @Test
//     public void obtenerClienteOk() throws Exception {
//         var cliente = getClienteValido();
//         Mockito.when(clienteService.obtenerCliente(12345678)).thenReturn(cliente);

//         mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.nombreCliente", is("Juan")));
//     }

//     @Test
//     public void obtenerClienteNoEncontrado() throws Exception {
//         Mockito.when(clienteService.obtenerCliente(12345678)).thenThrow(new RecursoNoEncontradoException("No encontrado"));

//         mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
//                 .andExpect(status().isNotFound())
//                 .andExpect(jsonPath("$.mensaje", is("No encontrado")));
//     }
//     // TEST PARA METODO LISTAR CLIENTE, AUN NO SE INCLUYEN CASOS DE ERROR PORQUE RESPUESTA VACIA NO ES UN ERROR, Y EL METODO NO DEPENDE DE PARAMETROS
//     //SE PODRIAN AGREGAR CASOS DE ERROR POR EJEMPLO PARA FALLA EN LA CONEXION A LA BBDD O SI SE DESEA LANZAR UNA EXEPCION CUANDO LA LISTA ESTA VACIA
//     @Test
//     public void listarClientesOk() throws Exception {
//         var cliente = getClienteValido();
//         Mockito.when(clienteService.listarClientes()).thenReturn(List.of(cliente));

//         mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.length()", is(1)))
//                 .andExpect(jsonPath("$[0].rutCliente", is(12345678)));
//     }
//     //TEST PARA METODO ACTUALIZAR CLIENTE CON CASOS DE ERROR
//     @Test
//     public void actualizarClienteOk() throws Exception {
//         var request = getClienteValido();
//         Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenReturn(request);

//         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(mapper.writeValueAsString(request)))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.email", is("juan@example.com")));
//     }

//     @Test
//     public void actualizarClienteCorreoDuplicado() throws Exception {
//         var request = getClienteValido();
//         Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenThrow(new CorreoDuplicadoException("Correo en uso"));

//         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(mapper.writeValueAsString(request)))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.mensaje", is("Correo en uso")));
//     }

//     @Test
//     public void actualizarClienteNoEncontrado() throws Exception {
//         var request = getClienteValido();
//         Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenThrow(new RecursoNoEncontradoException("Cliente no existe"));

//         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
//                         .contentType(MediaType.APPLICATION_JSON)
//                         .content(mapper.writeValueAsString(request)))
//                 .andExpect(status().isNotFound())
//                 .andExpect(jsonPath("$.mensaje", is("Cliente no existe")));
//     }
//     //TEST PARA METODO ELIMINAR CLIENTE CON CASO DE ERROR
//     @Test
//     public void eliminarClienteOk() throws Exception {
//         mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
//                 .andExpect(status().isNoContent());
//     }

//     @Test
//     public void eliminarClienteNoEncontrado() throws Exception {
//         Mockito.doThrow(new RecursoNoEncontradoException("No encontrado")).when(clienteService).eliminarCliente(12345678);

//         mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
//                 .andExpect(status().isNotFound())
//                 .andExpect(jsonPath("$.mensaje", is("No encontrado")));
//     }
// }
