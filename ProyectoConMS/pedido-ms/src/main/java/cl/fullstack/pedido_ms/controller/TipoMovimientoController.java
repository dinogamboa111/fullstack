package cl.fullstack.pedido_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pedido_ms.dto.TipoMovimientoDTO;
import cl.fullstack.pedido_ms.service.ITipoMovimientoService;

import java.util.List;

@RestController
@RequestMapping("/api/tipomovimientos")
public class TipoMovimientoController {

    @Autowired
    private ITipoMovimientoService tipoMovimientoService;

    @GetMapping
    public ResponseEntity<List<TipoMovimientoDTO>> getAllTipoMovimientos() {
        return ResponseEntity.ok(tipoMovimientoService.getAllTipoMovimientos());
    }

    @GetMapping("/{idTipoMovimiento}")
    public ResponseEntity<TipoMovimientoDTO> getTipoMovimientoById(@PathVariable int idTipoMovimiento) {
        return ResponseEntity.ok(tipoMovimientoService.getTipoMovimientoById(idTipoMovimiento));
    }

    @PostMapping
    public ResponseEntity<TipoMovimientoDTO> createTipoMovimiento(@RequestBody TipoMovimientoDTO tipoMovimientoDTO) {
        TipoMovimientoDTO nuevo = tipoMovimientoService.createTipoMovimiento(tipoMovimientoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PutMapping("/{idTipoMovimiento}")
    public ResponseEntity<TipoMovimientoDTO> updateTipoMovimiento(
            @PathVariable int idTipoMovimiento,
            @RequestBody TipoMovimientoDTO tipoMovimientoDTO) {
        return ResponseEntity.ok(tipoMovimientoService.updateTipoMovimiento(idTipoMovimiento, tipoMovimientoDTO));
    }

    @DeleteMapping("/{idTipoMovimiento}")
    public ResponseEntity<Void> deleteTipoMovimiento(@PathVariable int idTipoMovimiento) {
        tipoMovimientoService.deleteTipoMovimiento(idTipoMovimiento);
        return ResponseEntity.noContent().build();
    }
}