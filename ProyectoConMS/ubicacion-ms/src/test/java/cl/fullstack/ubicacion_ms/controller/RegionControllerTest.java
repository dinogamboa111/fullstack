package cl.fullstack.ubicacion_ms.controller;

import cl.fullstack.ubicacion_ms.dto.RegionDTO;
import cl.fullstack.ubicacion_ms.exception.IntegridadDatosException;
import cl.fullstack.ubicacion_ms.exception.RecursoDuplicadoException;
import cl.fullstack.ubicacion_ms.service.IRegionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegionController.class)
class RegionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IRegionService regionService;

    // Test para creación exitosa de region
    @Test
    void crearRegion_Exitoso() throws Exception {
        RegionDTO region = new RegionDTO(900, "MetropolitanaTESTING");
        when(regionService.crearRegion(any())).thenReturn(region);

        mockMvc.perform(post("/api/regiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idRegion").value(900))
                .andExpect(jsonPath("$.nombre").value("MetropolitanaTESTING"));
    }

    // Test para crear region que ya existe (error duplicado)
    @Test
    void crearRegion_Duplicado() throws Exception {
        RegionDTO region = new RegionDTO(900, "MetropolitanaTESTING");
        when(regionService.crearRegion(any()))
                .thenThrow(new RecursoDuplicadoException("Region duplicada"));

        mockMvc.perform(post("/api/regiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").value("Recurso duplicado"))
                .andExpect(jsonPath("$.mensaje").value("Region duplicada"));
    }

    // Test para actualización exitosa de region
    @Test
    void actualizarRegion_Exitoso() throws Exception {
        RegionDTO region = new RegionDTO(900, "Actualizada");
        when(regionService.actualizarRegion(eq(900), any())).thenReturn(region);

        mockMvc.perform(put("/api/regiones/900")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Actualizada"));
    }

    // Test para obtener region por ID exitosamente
    @Test
    void obtenerRegionPorId_Exitoso() throws Exception {
        RegionDTO region = new RegionDTO(900, "MetropolitanaTESTING");
        when(regionService.obtenerRegionPorId(900)).thenReturn(region);

        mockMvc.perform(get("/api/regiones/900"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("MetropolitanaTESTING"));
    }

    // Test para eliminar region exitosamente
    @Test
    void eliminarRegion_Exitoso() throws Exception {
        mockMvc.perform(delete("/api/regiones/900"))
                .andExpect(status().isNoContent());
    }

    // Test para eliminar region que tiene provincias asociadas (error de integridad)
    @Test
    void eliminarRegion_ConProvincias() throws Exception {
        doThrow(new IntegridadDatosException("Tiene provincias asociadas"))
                .when(regionService).eliminarRegion(900);

        mockMvc.perform(delete("/api/regiones/900"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje").value("Tiene provincias asociadas"));
    }
}
