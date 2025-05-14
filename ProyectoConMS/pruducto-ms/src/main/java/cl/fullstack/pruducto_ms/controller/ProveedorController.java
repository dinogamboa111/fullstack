package cl.fullstack.pruducto_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.fullstack.pruducto_ms.dto.ProveedorDTO;
import cl.fullstack.pruducto_ms.service.IProveedorService;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorDTO>> getAllProveedores() {
        return ResponseEntity.ok(proveedorService.getAllProveedores());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedorDTO> getProveedorById(@PathVariable int id) {
        return ResponseEntity.ok(proveedorService.getProveedorById(id));
    }

    @PostMapping
    public ResponseEntity<ProveedorDTO> createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.createProveedor(proveedorDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorDTO> updateProveedor(@PathVariable int id, @RequestBody ProveedorDTO proveedorDTO) {
        return ResponseEntity.ok(proveedorService.updateProveedor(id, proveedorDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProveedor(@PathVariable int id) {
        proveedorService.deleteProveedor(id);
        return ResponseEntity.noContent().build();
    }
}