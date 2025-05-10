package cl.fullstack.pruducto_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pruducto_ms.dto.ProveedorDTO;
import cl.fullstack.pruducto_ms.service.IProveedorService;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorService;

    @GetMapping
    public List<ProveedorDTO> getAllProveedores() {
        return proveedorService.getAllProveedores();
    }

    @GetMapping("/{id}")
    public ProveedorDTO getProveedorById(@PathVariable int id) {
        return proveedorService.getProveedorById(id);
    }

    @PostMapping
    public ProveedorDTO createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        return proveedorService.createProveedor(proveedorDTO);
    }

    @PutMapping("/{id}")
    public ProveedorDTO updateProveedor(@PathVariable int id, @RequestBody ProveedorDTO proveedorDTO) {
        return proveedorService.updateProveedor(id, proveedorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProveedor(@PathVariable int id) {
        proveedorService.deleteProveedor(id);
    }
}
