package cl.fullstack.ubicacion_ms.controller;

import cl.fullstack.ubicacion_ms.dto.ProvinciaDTO;
import cl.fullstack.ubicacion_ms.service.IProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provincias")
public class ProvinciaController {

    @Autowired
    private IProvinciaService provinciaService;

    @PostMapping
    public ResponseEntity<ProvinciaDTO> crearProvincia(@RequestBody ProvinciaDTO provincia) {
        return ResponseEntity.status(HttpStatus.CREATED).body(provinciaService.crearProvincia(provincia));
    }

    @GetMapping
    public ResponseEntity<List<ProvinciaDTO>> obtenerProvincias() {
        return ResponseEntity.ok(provinciaService.obtenerProvincias());
    }

    @GetMapping("/region/{idRegion}")
    public ResponseEntity<List<ProvinciaDTO>> obtenerProvinciasPorRegion(@PathVariable int idRegion) {
        return ResponseEntity.ok(provinciaService.obtenerProvinciasPorRegion(idRegion));
    }

    @GetMapping("/{idProvincia}")
    public ResponseEntity<ProvinciaDTO> obtenerProvinciaPorId(@PathVariable int idProvincia) {
        return ResponseEntity.ok(provinciaService.obtenerProvinciaPorId(idProvincia));
    }

    @DeleteMapping("/{idProvincia}")
    public ResponseEntity<Void> eliminarProvincia(@PathVariable int idProvincia) {
        provinciaService.eliminarProvincia(idProvincia);
        return ResponseEntity.noContent().build();
    }
@PutMapping("/{idProvincia}")  //nuevo put ojito
public ResponseEntity<ProvinciaDTO> actualizarProvincia(@PathVariable int idProvincia, @RequestBody ProvinciaDTO provincia) {
    return ResponseEntity.ok(provinciaService.actualizarProvincia(idProvincia, provincia));
}


}