package cl.fullstack.usuario_ms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.usuario_ms.dto.RolDTO;
import cl.fullstack.usuario_ms.service.IRolService;


import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private IRolService rolService;

    @PostMapping
    public ResponseEntity<RolDTO> crear(@RequestBody RolDTO rolDTO) {
        return ResponseEntity.ok(rolService.crearRol(rolDTO));
    }

    @GetMapping
    public ResponseEntity<List<RolDTO>> listar() {
        return ResponseEntity.ok(rolService.listarRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(rolService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolDTO> actualizar(@PathVariable Long id, @RequestBody RolDTO rolDTO) {
        return ResponseEntity.ok(rolService.actualizarRol(id, rolDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolService.eliminarRol(id);
        return ResponseEntity.noContent().build();
    }
}