package cl.fullstack.ubicacion_ms.controller;

import cl.fullstack.ubicacion_ms.dto.RegionDTO;
import cl.fullstack.ubicacion_ms.service.IRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/regiones")
public class RegionController {

    @Autowired
    private IRegionService regionService;
    
    @PostMapping
    public ResponseEntity<RegionDTO> crearRegion(@RequestBody RegionDTO region) {
        return ResponseEntity.status(HttpStatus.CREATED).body(regionService.crearRegion(region));
    }

    @GetMapping
    public ResponseEntity<List<RegionDTO>> obtenerRegiones() {
        return ResponseEntity.ok(regionService.obtenerRegiones());
    }

    @GetMapping("/{idRegion}")
    public ResponseEntity<RegionDTO> obtenerRegionPorId(@PathVariable int idRegion) {
        return ResponseEntity.ok(regionService.obtenerRegionPorId(idRegion));
    }

    @DeleteMapping("/{idRegion}")
    public ResponseEntity<Void> eliminarRegion(@PathVariable int idRegion) {
        regionService.eliminarRegion(idRegion);
        return ResponseEntity.noContent().build();
    }
@PutMapping("/{idRegion}")
public ResponseEntity<RegionDTO> actualizarRegion(@PathVariable int idRegion, @RequestBody RegionDTO region) {
    return ResponseEntity.ok(regionService.actualizarRegion(idRegion, region));
}
}
