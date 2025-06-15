package cl.fullstack.pruducto_ms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.fullstack.pruducto_ms.dto.CategoriaDTO;
import cl.fullstack.pruducto_ms.service.ICategoriaService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    // crear categoria
    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(categoriaDTO));
    }

    // eliminar catgoria
    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable int idCategoria) {
        String mensaje = categoriaService.eliminarCategoria(idCategoria);
        return ResponseEntity.ok(mensaje);
    }

    // modificar categoria
    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@Valid @PathVariable int idCategoria,
            @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(idCategoria, categoriaDTO));
    }

    // obtener categoria
    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(@PathVariable int idCategoria) {
        return ResponseEntity.ok(categoriaService.obtenerCategoria(idCategoria));
    }

    // listar categorias
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

}