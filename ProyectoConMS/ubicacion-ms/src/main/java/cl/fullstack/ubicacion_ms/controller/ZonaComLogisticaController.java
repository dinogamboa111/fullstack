package cl.fullstack.ubicacion_ms.controller;

import cl.fullstack.ubicacion_ms.dto.ZonaComLogisticaDTO;
import cl.fullstack.ubicacion_ms.service.IZonaComLogisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/zonas-logisticas")
public class ZonaComLogisticaController {

    @Autowired
    private IZonaComLogisticaService zonaComLogisticaService;

    @PostMapping
    public ResponseEntity<ZonaComLogisticaDTO> crearZonaLogistica(@RequestBody ZonaComLogisticaDTO zonaDTO) {
        ZonaComLogisticaDTO creada = zonaComLogisticaService.crearZonaComLogistica(zonaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping
    public ResponseEntity<List<ZonaComLogisticaDTO>> obtenerTodasLasZonas() {
        List<ZonaComLogisticaDTO> zonas = zonaComLogisticaService.obtenerZonaComLogistica();
        return ResponseEntity.ok(zonas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaComLogisticaDTO> obtenerZonaPorId(@PathVariable int id) {
        ZonaComLogisticaDTO zona = zonaComLogisticaService.obtenerZonaComLogisticaPorId(id);
        return ResponseEntity.ok(zona);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarZona(@PathVariable int id) {
        zonaComLogisticaService.eliminarZonaComLogistica(id);
        return ResponseEntity.noContent().build();
    }
}
