package cl.fullstack.ubicacion_ms.controller;

import cl.fullstack.ubicacion_ms.dto.ComunaDTO;
import cl.fullstack.ubicacion_ms.dto.ProvinciaDTO;
import cl.fullstack.ubicacion_ms.exception.IntegridadDatosException;
import cl.fullstack.ubicacion_ms.exception.NotFoundException;
import cl.fullstack.ubicacion_ms.service.IComunaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComunaController.class)
class ComunaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IComunaService comunaService;

    // Test para creacion exitosa de comuna
    @Test
    void crearComuna_Exitoso() throws Exception {
        ComunaDTO comuna = new ComunaDTO(1, "Puente Alto", new ProvinciaDTO(1, "Santiago", null));
        when(comunaService.crearComuna(any())).thenReturn(comuna);

        mockMvc.perform(post("/api/comunas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comuna)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Puente Alto"))
                .andExpect(jsonPath("$.provincia.nombre").value("Santiago"));
    }

    // Test para eliminar comuna con registros asociados (error integridad)
    @Test
    void eliminarComuna_ConRegistrosAsociados() throws Exception {
        doThrow(new IntegridadDatosException("Tiene direcciones asociadas"))
                .when(comunaService).eliminarComuna(1);

        mockMvc.perform(delete("/api/comunas/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("Tiene direcciones asociadas"))
                .andExpect(jsonPath("$.error").value("Error de integridad"));
    }

    // Test para obtener comunas por provincia
    @Test
    void obtenerComunasPorProvincia() throws Exception {
        List<ComunaDTO> comunas = List.of(
                new ComunaDTO(1, "Puente Alto", new ProvinciaDTO(1, "Santiago", null)),
                new ComunaDTO(2, "La Florida", new ProvinciaDTO(1, "Santiago", null))
        );
        when(comunaService.obtenerComunasPorProvincia(1)).thenReturn(comunas);

        mockMvc.perform(get("/api/comunas/provincia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(comunas.size()))
                .andExpect(jsonPath("$[0].nombre").value("Puente Alto"))
                .andExpect(jsonPath("$[1].nombre").value("La Florida"));
    }

    // Test para actualizar comuna con provincia inexistente (error NotFound)
    @Test
    void actualizarComuna_ProvinciaNoExiste() throws Exception {
        ComunaDTO comuna = new ComunaDTO(1, "Nueva Comuna", new ProvinciaDTO(999, "Inexistente", null));
        when(comunaService.actualizarComuna(eq(1), any()))
                .thenThrow(new NotFoundException("Provincia no encontrada"));

        mockMvc.perform(put("/api/comunas/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comuna)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso no encontrado"))
                .andExpect(jsonPath("$.mensaje").value("Provincia no encontrada"));
    }
}
