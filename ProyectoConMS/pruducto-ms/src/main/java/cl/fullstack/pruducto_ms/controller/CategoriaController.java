package cl.fullstack.pruducto_ms.controller;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.fullstack.pruducto_ms.dto.CategoriaDTO;
import cl.fullstack.pruducto_ms.service.ICategoriaService;
import jakarta.validation.Valid;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "Operaciones relacionadas con las categorías de productos")
public class CategoriaController {

    @Autowired
    private ICategoriaService categoriaService;

    @Operation(summary = "Crear una nueva categoría")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Categoría creada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    public ResponseEntity<CategoriaDTO> crearCategoria(
            @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(categoriaDTO));
    }

    @Operation(summary = "Actualizar una categoría existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> actualizarCategoria(
            @Parameter(description = "ID de la categoría a actualizar", example = "1")
            @Valid @PathVariable int idCategoria,
            @RequestBody CategoriaDTO categoriaDTO) {
        return ResponseEntity.ok(categoriaService.actualizarCategoria(idCategoria, categoriaDTO));
    }

    @Operation(summary = "Obtener una categoría por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoría encontrada"),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> obtenerCategoria(
            @Parameter(description = "ID de la categoría a consultar", example = "1")
            @PathVariable int idCategoria) {
        return ResponseEntity.ok(categoriaService.obtenerCategoria(idCategoria));
    }

    @Operation(summary = "Listar todas las categorías")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    })
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listarCategorias() {
        return ResponseEntity.ok(categoriaService.listarCategorias());
    }

}



    // eliminar catgoria-- se comenta ya que al eliminar este dato afectariamos la integridad de datos de la tabla producto
    // @DeleteMapping("/{idCategoria}")
    // public ResponseEntity<String> eliminarCategoria(@PathVariable int idCategoria) {
    //     String mensaje = categoriaService.eliminarCategoria(idCategoria);
    //     return ResponseEntity.ok(mensaje);
    // }