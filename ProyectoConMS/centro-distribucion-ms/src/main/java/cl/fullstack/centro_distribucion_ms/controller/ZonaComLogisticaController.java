package cl.fullstack.centro_distribucion_ms.controller;

import cl.fullstack.centro_distribucion_ms.dto.ZonaComLogisticaDTO;
import cl.fullstack.centro_distribucion_ms.service.IZonaComLogisticaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/zonas-logisticas")
public class ZonaComLogisticaController {

    @Autowired
    private IZonaComLogisticaService zonaComLogisticaService; // inyecta el servicio para manejar la logica

    @PostMapping
    public ResponseEntity<ZonaComLogisticaDTO> crearZonaLogistica(@RequestBody ZonaComLogisticaDTO zonaDTO) {
        // recibe datos para crear zona desde el cuerpo de la peticion
        ZonaComLogisticaDTO creada = zonaComLogisticaService.crearZonaComLogistica(zonaDTO); // crea la zona con el servicio
        return ResponseEntity.status(HttpStatus.CREATED).body(creada); // responde con estado 201 y la zona creada
    }

    @GetMapping
    public ResponseEntity<List<ZonaComLogisticaDTO>> obtenerTodasLasZonas() {
        // obtiene lista de todas las zonas desde el servicio
        List<ZonaComLogisticaDTO> zonas = zonaComLogisticaService.obtenerZonaComLogistica();
        return ResponseEntity.ok(zonas); // responde con lista y estado 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZonaComLogisticaDTO> obtenerZonaPorId(@PathVariable int id) {
        // recibe id en la url para buscar una zona especifica
        ZonaComLogisticaDTO zona = zonaComLogisticaService.obtenerZonaComLogisticaPorId(id); // obtiene la zona por id
        return ResponseEntity.ok(zona); // responde con la zona y estado 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarZona(@PathVariable int id) {
        // recibe id en la url para eliminar una zona
        zonaComLogisticaService.eliminarZonaComLogistica(id); // elimina la zona con el servicio
        return ResponseEntity.noContent().build(); // responde sin contenido con estado 204
    }
}
