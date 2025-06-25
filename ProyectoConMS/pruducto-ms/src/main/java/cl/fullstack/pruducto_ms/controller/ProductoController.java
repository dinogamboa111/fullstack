package cl.fullstack.pruducto_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.fullstack.pruducto_ms.dto.ProductoDTO;
import cl.fullstack.pruducto_ms.service.IProductoService;
import java.util.List;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con los productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(
            @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.createProducto(productoDTO));
    }

    @Operation(summary = "Eliminar un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<String> eliminar(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable int idProducto) {
        String mensaje = productoService.eliminarProducto(idProducto);
        return ResponseEntity.ok(mensaje);
    }

    @Operation(summary = "Actualizar un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> actualizarProducto(
            @Parameter(description = "ID del producto a actualizar", example = "1")
            @PathVariable int idProducto,
            @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.actualizarProducto(idProducto, productoDTO));
    }

    @Operation(summary = "Obtener un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> obtenerProducto(
            @Parameter(description = "ID del producto a consultar", example = "1")
            @PathVariable int idProducto) {
        return ResponseEntity.ok(productoService.obtenerProducto(idProducto));
    }

    @Operation(summary = "Listar todos los productos")
    @ApiResponse(responseCode = "200", description = "Listado de productos obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @Operation(summary = "Crear múltiples productos en lote")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Productos creados exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/loteproductos")
    public ResponseEntity<List<ProductoDTO>> crearProductosEnLote(
            @RequestBody List<ProductoDTO> productos) {
        List<ProductoDTO> guardados = productoService.crearProductosEnLote(productos);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardados);
    }

}
