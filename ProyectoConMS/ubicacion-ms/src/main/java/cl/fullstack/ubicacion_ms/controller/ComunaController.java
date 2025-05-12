package cl.fullstack.ubicacion_ms.controller;

import cl.fullstack.ubicacion_ms.dto.ComunaDTO;
import cl.fullstack.ubicacion_ms.service.IComunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/comunas")
public class ComunaController {

    @Autowired
    private IComunaService comunaService;

    // @PostMapping
    // public ResponseEntity<ComunaDTO> crearComuna(@RequestBody ComunaDTO comuna) {
    //     ComunaDTO nuevaComuna = comunaService.crearComuna(comuna);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(nuevaComuna);
    // }
    // linea de codigo de mas, en usar un nuevaComuna, desp te explico en clase si lees esto jaime   
    @PostMapping
    public ResponseEntity<ComunaDTO> crearComuna(@RequestBody ComunaDTO comuna) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaService.crearComuna(comuna));
    }

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> obtenerComunas() {
        return ResponseEntity.ok(comunaService.obtenerComunas());
    }

    @GetMapping("/provincia/{idProvincia}")
    public ResponseEntity<List<ComunaDTO>> obtenerComunasPorProvincia(@PathVariable int idProvincia) {
        return ResponseEntity.ok(comunaService.obtenerComunasPorProvincia(idProvincia));
    }

    @GetMapping("/{idComuna}")
    public ResponseEntity<ComunaDTO> obtenerComunaPorId(@PathVariable int idComuna) {
        return ResponseEntity.ok(comunaService.obtenerComunaPorId(idComuna));
    }

    @DeleteMapping("/{idComuna}")
    public ResponseEntity<Void> eliminarComuna(@PathVariable int idComuna) {
        comunaService.eliminarComuna(idComuna);
        return ResponseEntity.noContent().build();
    }
}
