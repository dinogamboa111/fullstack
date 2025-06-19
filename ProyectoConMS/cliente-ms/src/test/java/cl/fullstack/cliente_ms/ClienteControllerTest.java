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
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



// @WebMvcTest(ClienteController.class) // Testea solo el controlador ClienteController, no todo el contexto de Spring
// public class ClienteControllerTest { // Clase de prueba para el controlador ClienteController

//     @Autowired
//     private MockMvc mvc; // Inyección de MockMvc para simular peticiones HTTP al controlador

//     @MockBean
//     private IClienteService clienteService; // Se simula el servicio para no usar lógica real ni base de datos

//     private final ObjectMapper mapper = new ObjectMapper(); // Para convertir objetos Java a JSON y viceversa

//     private final String BASE_URL = "/api/clientes"; // Ruta base común usada en las peticiones

//     private void imprimirEncabezado(String nombreTest) { // imprimir un encabezado por consola
//         System.out.println("\n===========================");
//         System.out.println("=== Ejecutando test: " + nombreTest + " ===");
//         System.out.println("===========================\n");
//     }

//     private ClienteDTO getClienteValido() { // Método que devuelve un cliente válido de ejemplo
//         return new ClienteDTO(12345678, 'K', "Juan", "Pérez", "Gonzalez", "912345678", "juan@example.com", "123",
//                 "Av. Siempre Viva", 1);
//     }

//     @Test
//     public void crearClienteOk() throws Exception { // Test para creación exitosa de un cliente
//         var request = getClienteValido(); // Cliente de prueba
//         Mockito.when(clienteService.crearCliente(any())).thenReturn(request); // Simula que el servicio retorna el mismo cliente

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL) // Se hace POST a /api/clientes
//                 .contentType(MediaType.APPLICATION_JSON) // Se indica tipo de contenido JSON
//                 .content(mapper.writeValueAsString(request))) // Se envía el cliente como JSON
//                 .andDo(result -> imprimirEncabezado("crearClienteOk")) // Imprime encabezado del test
//                 .andDo(print()) // Muestra detalles de la petición/respuesta en consola
//                 .andExpect(status().isCreated()) // Se espera código HTTP 201
//                 .andExpect(jsonPath("$.rutCliente", is(request.getRutCliente()))); // Se verifica el rut en la respuesta
//     }

//     @Test
//     public void crearClienteRutDuplicado() throws Exception { // Test para cuando el RUT ya existe
//         var request = getClienteValido();
//         Mockito.when(clienteService.crearCliente(any())).thenThrow(new RecursoDuplicadoException("RUT duplicado")); // Simula excepción por RUT repetido

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(request)))
//                 .andDo(result -> imprimirEncabezado("crearClienteRutDuplicado"))
//                 .andDo(print())
//                 .andExpect(status().isConflict()) // Se espera HTTP 409
//                 .andExpect(jsonPath("$.mensaje", is("RUT duplicado"))); // Se valida el mensaje de error
//     }

//     @Test
//     public void crearClienteCorreoDuplicado() throws Exception { // Test para correo duplicado
//         var request = getClienteValido();
//         Mockito.when(clienteService.crearCliente(any())).thenThrow(new CorreoDuplicadoException("Correo duplicado"));

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(request)))
//                 .andDo(result -> imprimirEncabezado("crearClienteCorreoDuplicado"))
//                 .andDo(print())
//                 .andExpect(status().isBadRequest()) // Se espera HTTP 400
//                 .andExpect(jsonPath("$.mensaje", is("Correo duplicado")));
//     }

//     @Test
//     public void crearClienteDatosInvalidos() throws Exception { // Test para datos inválidos
//         var request = getClienteValido();
//         Mockito.when(clienteService.crearCliente(any())).thenThrow(new DatosInvalidosException("Datos inválidos"));

//         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(request)))
//                 .andDo(result -> imprimirEncabezado("crearClienteDatosInvalidos"))
//                 .andDo(print())
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.mensaje", is("Datos inválidos")));
//     }

//     @Test
//     public void obtenerClienteOk() throws Exception { // Test para obtener un cliente existente
//         var cliente = getClienteValido();
//         Mockito.when(clienteService.obtenerCliente(12345678)).thenReturn(cliente); // Simula cliente encontrado

//         mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678")) // GET a /api/clientes/12345678
//                 .andDo(result -> imprimirEncabezado("obtenerClienteOk"))
//                 .andDo(print())
//                 .andExpect(status().isOk()) // Se espera HTTP 200
//                 .andExpect(jsonPath("$.nombreCliente", is("Juan"))); // Se valida el nombre en la respuesta
//     }

//     @Test
//     public void obtenerClienteNoEncontrado() throws Exception { // Test para cliente no encontrado
//         Mockito.when(clienteService.obtenerCliente(12345678))
//                 .thenThrow(new RecursoNoEncontradoException("No encontrado")); // Simula que no existe

//         mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
//                 .andDo(result -> imprimirEncabezado("obtenerClienteNoEncontrado"))
//                 .andDo(print())
//                 .andExpect(status().isNotFound()) // Se espera HTTP 404
//                 .andExpect(jsonPath("$.mensaje", is("No encontrado")));
//     }

//     @Test
//     public void listarClientesOk() throws Exception { // Test para listar todos los clientes
//         var cliente = getClienteValido();
//         Mockito.when(clienteService.listarClientes()).thenReturn(List.of(cliente)); // Simula una lista con 1 cliente

//         mvc.perform(MockMvcRequestBuilders.get(BASE_URL)) // GET a /api/clientes
//                 .andDo(result -> imprimirEncabezado("listarClientesOk"))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.length()", is(1))) // Se espera 1 cliente
//                 .andExpect(jsonPath("$[0].rutCliente", is(12345678))); // Se verifica su rut
//     }

//     @Test
//     public void actualizarClienteOk() throws Exception { // Test para actualizar cliente exitosamente
//         var request = getClienteValido();
//         Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenReturn(request); // Simula actualización exitosa

//         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(request)))
//                 .andDo(result -> imprimirEncabezado("actualizarClienteOk"))
//                 .andDo(print())
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.email", is("juan@example.com"))); // Verifica que el email no cambió
//     }

//     @Test
//     public void actualizarClienteCorreoDuplicado() throws Exception { // Test para actualizar con correo en uso
//         var request = getClienteValido();
//         Mockito.when(clienteService.actualizarCliente(eq(12345678), any()))
//                 .thenThrow(new CorreoDuplicadoException("Correo en uso")); // Simula correo ya registrado

//         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(request)))
//                 .andDo(result -> imprimirEncabezado("actualizarClienteCorreoDuplicado"))
//                 .andDo(print())
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.mensaje", is("Correo en uso")));
//     }

//     @Test
//     public void actualizarClienteNoEncontrado() throws Exception { // Test para actualizar cliente inexistente
//         var request = getClienteValido();
//         Mockito.when(clienteService.actualizarCliente(eq(12345678), any()))
//                 .thenThrow(new RecursoNoEncontradoException("Cliente no existe"));

//         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content(mapper.writeValueAsString(request)))
//                 .andDo(result -> imprimirEncabezado("actualizarClienteNoEncontrado"))
//                 .andDo(print())
//                 .andExpect(status().isNotFound())
//                 .andExpect(jsonPath("$.mensaje", is("Cliente no existe")));
//     }

//     @Test
//     public void eliminarClienteOk() throws Exception { // Test para eliminar cliente correctamente
//         mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
//                 .andDo(result -> imprimirEncabezado("eliminarClienteOk"))
//                 .andDo(print())
//                 .andExpect(status().isNoContent()); // Se espera HTTP 204 sin cuerpo
//     }

//     @Test
//     public void eliminarClienteNoEncontrado() throws Exception { // Test para eliminar cliente que no existe
//         Mockito.doThrow(new RecursoNoEncontradoException("No encontrado")).when(clienteService)
//                 .eliminarCliente(12345678); // Simula que no existe el cliente a eliminar

//         mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
//                 .andDo(result -> imprimirEncabezado("eliminarClienteNoEncontrado"))
//                 .andDo(print())
//                 .andExpect(status().isNotFound())
//                 .andExpect(jsonPath("$.mensaje", is("No encontrado")));
//     }
// }



// // package cl.fullstack.cliente_ms;

// // import cl.fullstack.cliente_ms.controller.ClienteController;
// // import cl.fullstack.cliente_ms.dto.ClienteDTO;
// // import cl.fullstack.cliente_ms.exception.CorreoDuplicadoException;
// // import cl.fullstack.cliente_ms.exception.DatosInvalidosException;
// // import cl.fullstack.cliente_ms.exception.RecursoDuplicadoException;
// // import cl.fullstack.cliente_ms.exception.RecursoNoEncontradoException;
// // import cl.fullstack.cliente_ms.service.IClienteService;
// // import com.fasterxml.jackson.databind.ObjectMapper;
// // import org.junit.jupiter.api.Test;
// // import org.mockito.Mockito;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// // import org.springframework.boot.test.mock.mockito.MockBean;
// // import org.springframework.http.MediaType;
// // import org.springframework.test.web.servlet.MockMvc;

// // import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// // import java.util.List;

// // import static org.hamcrest.Matchers.is;
// // import static org.mockito.ArgumentMatchers.any;
// // import static org.mockito.ArgumentMatchers.eq;
// // import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// // import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// // @WebMvcTest(ClienteController.class)
// // public class ClienteControllerTest {

// //     @Autowired
// //     private MockMvc mvc;

// //     @MockBean
// //     private IClienteService clienteService;

// //     private final ObjectMapper mapper = new ObjectMapper();

// //     private final String BASE_URL = "/api/clientes";

// //     private void imprimirEncabezado(String nombreTest) {
// //         System.out.println("\n===========================");
// //         System.out.println("=== Ejecutando test: " + nombreTest + " ===");
// //         System.out.println("===========================\n");
// //     }

// //     private ClienteDTO getClienteValido() {
// //         return new ClienteDTO(12345678, 'K', "Juan", "Pérez", "Gonzalez", "912345678", "juan@example.com", "123",
// //                 "Av. Siempre Viva", 1);
// //     }

// //     @Test
// //     public void crearClienteOk() throws Exception {
// //         var request = getClienteValido();
// //         Mockito.when(clienteService.crearCliente(any())).thenReturn(request);

// //         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(mapper.writeValueAsString(request)))
// //                 .andDo(result -> imprimirEncabezado("crearClienteOk"))
// //                 .andDo(print())
// //                 .andExpect(status().isCreated())
// //                 .andExpect(jsonPath("$.rutCliente", is(request.getRutCliente())));
// //     }

// //     @Test
// //     public void crearClienteRutDuplicado() throws Exception {
// //         var request = getClienteValido();
// //         Mockito.when(clienteService.crearCliente(any())).thenThrow(new RecursoDuplicadoException("RUT duplicado"));

// //         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(mapper.writeValueAsString(request)))
// //                 .andDo(result -> imprimirEncabezado("crearClienteRutDuplicado"))
// //                 .andDo(print())
// //                 .andExpect(status().isConflict())
// //                 .andExpect(jsonPath("$.mensaje", is("RUT duplicado")));
// //     }

// //     @Test
// //     public void crearClienteCorreoDuplicado() throws Exception {
// //         var request = getClienteValido();
// //         Mockito.when(clienteService.crearCliente(any())).thenThrow(new CorreoDuplicadoException("Correo duplicado"));

// //         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(mapper.writeValueAsString(request)))
// //                 .andDo(result -> imprimirEncabezado("crearClienteCorreoDuplicado"))
// //                 .andDo(print())
// //                 .andExpect(status().isBadRequest())
// //                 .andExpect(jsonPath("$.mensaje", is("Correo duplicado")));
// //     }

// //     @Test
// //     public void crearClienteDatosInvalidos() throws Exception {
// //         var request = getClienteValido();
// //         Mockito.when(clienteService.crearCliente(any())).thenThrow(new DatosInvalidosException("Datos inválidos"));

// //         mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(mapper.writeValueAsString(request)))
// //                 .andDo(result -> imprimirEncabezado("crearClienteDatosInvalidos"))
// //                 .andDo(print())
// //                 .andExpect(status().isBadRequest())
// //                 .andExpect(jsonPath("$.mensaje", is("Datos inválidos")));
// //     }

// //     @Test
// //     public void obtenerClienteOk() throws Exception {
// //         var cliente = getClienteValido();
// //         Mockito.when(clienteService.obtenerCliente(12345678)).thenReturn(cliente);

// //         mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
// //                 .andDo(result -> imprimirEncabezado("obtenerClienteOk"))
// //                 .andDo(print())
// //                 .andExpect(status().isOk())
// //                 .andExpect(jsonPath("$.nombreCliente", is("Juan")));
// //     }

// //     @Test
// //     public void obtenerClienteNoEncontrado() throws Exception {
// //         Mockito.when(clienteService.obtenerCliente(12345678))
// //                 .thenThrow(new RecursoNoEncontradoException("No encontrado"));

// //         mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
// //                 .andDo(result -> imprimirEncabezado("obtenerClienteNoEncontrado"))
// //                 .andDo(print())
// //                 .andExpect(status().isNotFound())
// //                 .andExpect(jsonPath("$.mensaje", is("No encontrado")));
// //     }

// //     @Test
// //     public void listarClientesOk() throws Exception {
// //         var cliente = getClienteValido();
// //         Mockito.when(clienteService.listarClientes()).thenReturn(List.of(cliente));

// //         mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
// //                 .andDo(result -> imprimirEncabezado("listarClientesOk"))
// //                 .andDo(print())
// //                 .andExpect(status().isOk())
// //                 .andExpect(jsonPath("$.length()", is(1)))
// //                 .andExpect(jsonPath("$[0].rutCliente", is(12345678)));
// //     }

// //     @Test
// //     public void actualizarClienteOk() throws Exception {
// //         var request = getClienteValido();
// //         Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenReturn(request);

// //         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(mapper.writeValueAsString(request)))
// //                 .andDo(result -> imprimirEncabezado("actualizarClienteOk"))
// //                 .andDo(print())
// //                 .andExpect(status().isOk())
// //                 .andExpect(jsonPath("$.email", is("juan@example.com")));
// //     }

// //     @Test
// //     public void actualizarClienteCorreoDuplicado() throws Exception {
// //         var request = getClienteValido();
// //         Mockito.when(clienteService.actualizarCliente(eq(12345678), any()))
// //                 .thenThrow(new CorreoDuplicadoException("Correo en uso"));

// //         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(mapper.writeValueAsString(request)))
// //                 .andDo(result -> imprimirEncabezado("actualizarClienteCorreoDuplicado"))
// //                 .andDo(print())
// //                 .andExpect(status().isBadRequest())
// //                 .andExpect(jsonPath("$.mensaje", is("Correo en uso")));
// //     }

// //     @Test
// //     public void actualizarClienteNoEncontrado() throws Exception {
// //         var request = getClienteValido();
// //         Mockito.when(clienteService.actualizarCliente(eq(12345678), any()))
// //                 .thenThrow(new RecursoNoEncontradoException("Cliente no existe"));

// //         mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
// //                 .contentType(MediaType.APPLICATION_JSON)
// //                 .content(mapper.writeValueAsString(request)))
// //                 .andDo(result -> imprimirEncabezado("actualizarClienteNoEncontrado"))
// //                 .andDo(print())
// //                 .andExpect(status().isNotFound())
// //                 .andExpect(jsonPath("$.mensaje", is("Cliente no existe")));
// //     }

// //     @Test
// //     public void eliminarClienteOk() throws Exception {
// //         mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
// //                 .andDo(result -> imprimirEncabezado("eliminarClienteOk"))
// //                 .andDo(print())
// //                 .andExpect(status().isNoContent());
// //     }

// //     @Test
// //     public void eliminarClienteNoEncontrado() throws Exception {
// //         Mockito.doThrow(new RecursoNoEncontradoException("No encontrado")).when(clienteService)
// //                 .eliminarCliente(12345678);

// //         mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
// //                 .andDo(result -> imprimirEncabezado("eliminarClienteNoEncontrado"))
// //                 .andDo(print())
// //                 .andExpect(status().isNotFound())
// //                 .andExpect(jsonPath("$.mensaje", is("No encontrado")));
// //     }
// // }


