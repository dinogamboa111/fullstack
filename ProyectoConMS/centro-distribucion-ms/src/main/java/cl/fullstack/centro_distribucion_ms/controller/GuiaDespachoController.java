package cl.fullstack.centro_distribucion_ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoDTO;
import cl.fullstack.centro_distribucion_ms.service.IGuiaDespachoService;

@RestController
@RequestMapping("/api/guias-despacho")
public class GuiaDespachoController {

    @Autowired
    private IGuiaDespachoService guiaDespachoService;

    // Crear una nueva guía de despacho
    @PostMapping
    public ResponseEntity<GuiaDespachoDTO> crearGuia(@RequestBody GuiaDespachoDTO guiaDespachoDTO) {
        GuiaDespachoDTO guiaCreada = guiaDespachoService.crearGuiaDespacho(guiaDespachoDTO);
        return ResponseEntity.ok(guiaCreada);
    }

    // Obtener todas las guías
    @GetMapping
    public ResponseEntity<List<GuiaDespachoDTO>> listarGuias() {
        List<GuiaDespachoDTO> listaGuias = guiaDespachoService.obtenerTodasLasGuias();
        return ResponseEntity.ok(listaGuias);
    }

    // Obtener una guía por ID
    @GetMapping("/{id}")
    public ResponseEntity<GuiaDespachoDTO> obtenerGuiaPorId(@PathVariable int id) {
        GuiaDespachoDTO guia = guiaDespachoService.obtenerGuiaPorId(id);
        return ResponseEntity.ok(guia);
    }

    // Eliminar una guía por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGuia(@PathVariable int id) {
        guiaDespachoService.eliminarGuia(id);
        return ResponseEntity.noContent().build();
    }


}
