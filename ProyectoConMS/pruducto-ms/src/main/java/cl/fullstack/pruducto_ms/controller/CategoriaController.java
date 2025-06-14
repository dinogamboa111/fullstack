package cl.fullstack.pruducto_ms.controller;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pruducto_ms.dto.CategoriaDTO;
import cl.fullstack.pruducto_ms.service.ICategoriaService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    // crear categoria
    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(categoriaDTO));
    }
    //eliminar catgoria
   // @DeleteMapping("/{idCategoria}")
    //public ResponseEntity<CategoriaDTO> eliminarCategoria(@PathVariable int idCategoria) {
      //  CategoriaDTO dtoEliminado = categoriaService.eliminarCategoria(idCategoria);
        //return ResponseEntity.ok(dtoEliminado); // devuelve el DTO como JSON
    //}

    @DeleteMapping("/{idCategoria}")
public ResponseEntity<Map<String, Object>> eliminarCategoria(@PathVariable int idCategoria) {
    CategoriaDTO dto = categoriaService.eliminarCategoria(idCategoria);

    Map<String, Object> respuesta = new HashMap<>();
    respuesta.put("mensaje", "Categoría eliminada correctamente");
    respuesta.put("categoria", dto);

    return ResponseEntity.ok(respuesta);
}
    // MODIFICAR CATEGORIA
    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(@Valid @PathVariable int idCategoria,
            @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(idCategoria, categoriaDTO));
    }

    // OBTENER CATEGORIA

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(@PathVariable int idCategoria) {
        return ResponseEntity.ok(categoriaService.obtenerCategoria(idCategoria));
    }

    // LISTAR CATEGORIAS

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

}