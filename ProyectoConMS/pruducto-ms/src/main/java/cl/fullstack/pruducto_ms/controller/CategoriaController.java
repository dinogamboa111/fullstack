package cl.fullstack.pruducto_ms.controller;
import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pruducto_ms.dto.CategoriaDTO;
import cl.fullstack.pruducto_ms.service.ICategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.getAllCategorias());
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable int idCategoria) {
        return ResponseEntity.ok(categoriaService.getCategoriaById(idCategoria));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.createCategoria(categoriaDTO));
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable int idCategoria, @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaService.updateCategoria(idCategoria, categoriaDTO));
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> deleteCategoria(@PathVariable int idCategoria) {
        categoriaService.deleteCategoria(idCategoria);
        return ResponseEntity.noContent().build();
    }
}