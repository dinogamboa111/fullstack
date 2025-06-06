package cl.fullstack.cliente_ms;


import cl.fullstack.cliente_ms.controller.ClienteController;
import cl.fullstack.cliente_ms.dto.ClienteDTO;
import cl.fullstack.cliente_ms.exception.CorreoDuplicadoException;
import cl.fullstack.cliente_ms.exception.DatosInvalidosException;
import cl.fullstack.cliente_ms.exception.RecursoDuplicadoException;
import cl.fullstack.cliente_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.cliente_ms.service.IClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IClienteService clienteService;

    private final ObjectMapper mapper = new ObjectMapper();
    private final String BASE_URL = "/api/clientes";

    private ClienteDTO getClienteValido() {
        return new ClienteDTO(12345678, 'K', "Juan", "Pérez", "Gonzalez", "912345678", "juan@example.com", "123", "Av. Siempre Viva", 1);
    }
    //TEST PARA METODO CREAR, CON CASOS DE ERROR
    @Test
    public void crearClienteOk() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.crearCliente(any())).thenReturn(request);

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rutCliente", is(request.getRutCliente())));
    }

    @Test
    public void crearClienteRutDuplicado() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.crearCliente(any())).thenThrow(new RecursoDuplicadoException("RUT duplicado"));

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje", is("RUT duplicado")));
    }

    @Test
    public void crearClienteCorreoDuplicado() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.crearCliente(any())).thenThrow(new CorreoDuplicadoException("Correo duplicado"));

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", is("Correo duplicado")));
    }

    @Test
    public void crearClienteDatosInvalidos() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.crearCliente(any())).thenThrow(new DatosInvalidosException("Datos inválidos"));

        mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", is("Datos inválidos")));
    }
    //TEST PARA METODO OBTENER CLIENTE CON CASOS DE ERROR
    @Test
    public void obtenerClienteOk() throws Exception {
        var cliente = getClienteValido();
        Mockito.when(clienteService.obtenerCliente(12345678)).thenReturn(cliente);

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreCliente", is("Juan")));
    }

    @Test
    public void obtenerClienteNoEncontrado() throws Exception {
        Mockito.when(clienteService.obtenerCliente(12345678)).thenThrow(new RecursoNoEncontradoException("No encontrado"));

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/12345678"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("No encontrado")));
    }
    // TEST PARA METODO LISTAR CLIENTE, AUN NO SE INCLUYEN CASOS DE ERROR PORQUE RESPUESTA VACIA NO ES UN ERROR, Y EL METODO NO DEPENDE DE PARAMETROS
    //SE PODRIAN AGREGAR CASOS DE ERROR POR EJEMPLO PARA FALLA EN LA CONEXION A LA BBDD O SI SE DESEA LANZAR UNA EXEPCION CUANDO LA LISTA ESTA VACIA
    @Test
    public void listarClientesOk() throws Exception {
        var cliente = getClienteValido();
        Mockito.when(clienteService.listarClientes()).thenReturn(List.of(cliente));

        mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].rutCliente", is(12345678)));
    }
    //TEST PARA METODO ACTUALIZAR CLIENTE CON CASOS DE ERROR
    @Test
    public void actualizarClienteOk() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenReturn(request);

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("juan@example.com")));
    }

    @Test
    public void actualizarClienteCorreoDuplicado() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenThrow(new CorreoDuplicadoException("Correo en uso"));

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensaje", is("Correo en uso")));
    }

    @Test
    public void actualizarClienteNoEncontrado() throws Exception {
        var request = getClienteValido();
        Mockito.when(clienteService.actualizarCliente(eq(12345678), any())).thenThrow(new RecursoNoEncontradoException("Cliente no existe"));

        mvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("Cliente no existe")));
    }
    //TEST PARA METODO ELIMINAR CLIENTE CON CASO DE ERROR
    @Test
    public void eliminarClienteOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void eliminarClienteNoEncontrado() throws Exception {
        Mockito.doThrow(new RecursoNoEncontradoException("No encontrado")).when(clienteService).eliminarCliente(12345678);

        mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/12345678"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("No encontrado")));
    }
}
