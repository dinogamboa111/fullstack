package cl.fullstack.pedido_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<TipoMovimientoDTO> getAllTipoMovimiento() {
        return tipoMovimientoService.getAllTipoMovimientos();
    }

    @GetMapping("/{idTipoMovimiento}")
    public TipoMovimientoDTO getTipoMovimientoById(@PathVariable int idTipoMovimiento) {
        return tipoMovimientoService.getTipoMovimientoById(idTipoMovimiento);
    }

    @PostMapping
    public TipoMovimientoDTO createUsuario(@RequestBody TipoMovimientoDTO tipoMovimientoDTO) {
        return tipoMovimientoService.createTipoMovimiento(tipoMovimientoDTO);
    }

    @PutMapping("/{idTipoMovimiento}")
    public TipoMovimientoDTO updateUsuario(@PathVariable int idTipoMovimiento,
            @RequestBody TipoMovimientoDTO tipoMovimientoDTO) {
        return tipoMovimientoService.updateTipoMovimiento(idTipoMovimiento, tipoMovimientoDTO);
    }

    @DeleteMapping("/{idTipoMovimiento}")
    public void deleteUsuario(@PathVariable int idTipoMovimiento) {
        tipoMovimientoService.deleteTipoMovimiento(idTipoMovimiento);
    }

}
