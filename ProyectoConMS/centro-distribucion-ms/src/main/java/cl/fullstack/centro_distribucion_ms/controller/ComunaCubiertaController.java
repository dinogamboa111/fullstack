package cl.fullstack.centro_distribucion_ms.controller;

import cl.fullstack.centro_distribucion_ms.dto.ComunaCubiertaDTO;
import cl.fullstack.centro_distribucion_ms.service.IComunaCubiertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // indica que esta clase es un controlador rest
@RequestMapping("api/comunas-cubiertas") // ruta base del controlador
public class ComunaCubiertaController {

    @Autowired
    private IComunaCubiertaService comunaCubiertaService; // inyecta el servicio

    @PostMapping
    public ResponseEntity<ComunaCubiertaDTO> crear(@RequestBody ComunaCubiertaDTO dto) {
        // recibe el dto desde el cuerpo de la peticion
        ComunaCubiertaDTO creado = comunaCubiertaService.crearComunaCubierta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // responde con dto creado y estado 201
    }

    @GetMapping
    public ResponseEntity<List<ComunaCubiertaDTO>> obtenerTodas() {
        // obtiene todas las comunas cubiertas
        List<ComunaCubiertaDTO> lista = comunaCubiertaService.obtenerTodas();
        return ResponseEntity.ok(lista); // responde con lista y estado 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        // elimina una comuna cubierta por su id
        comunaCubiertaService.eliminarPorId(id);
        return ResponseEntity.noContent().build(); // responde sin contenido con estado 204
    }
}
