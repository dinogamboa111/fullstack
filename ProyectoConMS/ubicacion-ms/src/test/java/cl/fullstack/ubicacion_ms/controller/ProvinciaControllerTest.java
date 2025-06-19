package cl.fullstack.ubicacion_ms.controller;

import cl.fullstack.ubicacion_ms.dto.ProvinciaDTO;
import cl.fullstack.ubicacion_ms.dto.RegionDTO;
import cl.fullstack.ubicacion_ms.exception.NotFoundException;
import cl.fullstack.ubicacion_ms.service.IProvinciaService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProvinciaController.class)
class ProvinciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IProvinciaService provinciaService;

    // Test para crear provincia con exito
    @Test
    void crearProvincia_Exitoso() throws Exception {
        ProvinciaDTO provincia = new ProvinciaDTO(1, "Santiago", new RegionDTO(1, "Metropolitana"));
        when(provinciaService.crearProvincia(any())).thenReturn(provincia);

        mockMvc.perform(post("/api/provincias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provincia)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Santiago"))
                .andExpect(jsonPath("$.region.nombre").value("Metropolitana"));
    }

    // Test para obtener provincias filtrando por region
    @Test
    void obtenerProvinciasPorRegion() throws Exception {
        List<ProvinciaDTO> provincias = List.of(
            new ProvinciaDTO(1, "Santiago", new RegionDTO(1, "Metropolitana")),
            new ProvinciaDTO(2, "Chacabuco", new RegionDTO(1, "Metropolitana"))
        );
        when(provinciaService.obtenerProvinciasPorRegion(1)).thenReturn(provincias);

        mockMvc.perform(get("/api/provincias/region/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(provincias.size()))
                .andExpect(jsonPath("$[0].nombre").value("Santiago"))
                .andExpect(jsonPath("$[1].nombre").value("Chacabuco"));
    }

    // Test para actualizar provincia con región invalida (error NotFound)
    @Test
    void actualizarProvincia_RegionInvalida() throws Exception {
        ProvinciaDTO provincia = new ProvinciaDTO(1, "Santiago", new RegionDTO(999, "Invalida"));
        when(provinciaService.actualizarProvincia(eq(1), any()))
            .thenThrow(new NotFoundException("Region no encontrada"));

        mockMvc.perform(put("/api/provincias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(provincia)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Recurso no encontrado"))
                .andExpect(jsonPath("$.mensaje").value("Region no encontrada"));
    }
}
