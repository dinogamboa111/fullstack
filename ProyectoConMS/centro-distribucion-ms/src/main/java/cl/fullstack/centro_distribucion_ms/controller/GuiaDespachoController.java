
package cl.fullstack.centro_distribucion_ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoCreateRequest;
import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoDTO;
import cl.fullstack.centro_distribucion_ms.dto.GuiaDespachoResponseDTO;
import cl.fullstack.centro_distribucion_ms.service.IGuiaDespachoService;

@RestController
@RequestMapping("/api/guias")
public class GuiaDespachoController {

    @Autowired
    private IGuiaDespachoService guiaDespachoService;

    // endpoint para crear guia
    @PostMapping
    public ResponseEntity<GuiaDespachoResponseDTO> crearGuia(@RequestBody GuiaDespachoCreateRequest request) {
        GuiaDespachoResponseDTO response = guiaDespachoService.crearGuiaDespacho(request.getIdDespachador());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // endpoint para eliminar guia
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarGuia(@PathVariable int id) {
        String mensaje = guiaDespachoService.eliminarGuiaDespacho(id);
        return ResponseEntity.ok(mensaje);
    }

    // endpoint para listar una guia
    @GetMapping("/{id}")
    public ResponseEntity<GuiaDespachoDTO> getPedidoById(@PathVariable int id) {
        return ResponseEntity.ok(guiaDespachoService.getGuiaDespachoById(id));
    }

    // endpoint para listar todas las guias
    @GetMapping
    public ResponseEntity<List<GuiaDespachoDTO>> listarTodasLasGuias() {
        List<GuiaDespachoDTO> guias = guiaDespachoService.getAllGuiasDespacho();
        return ResponseEntity.ok(guias);
    }
    //endpoint para modificar guia FALTA

}
