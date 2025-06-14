package cl.fullstack.pruducto_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pruducto_ms.dto.ProductoDTO;
import cl.fullstack.pruducto_ms.service.IProductoService;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable int idProducto) {
        return ResponseEntity.ok(productoService.obtenerProducto(idProducto));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto( @Valid @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.createProducto(productoDTO));
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable int idProducto, @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.actualizarProducto(idProducto, productoDTO));
    }
@DeleteMapping("/{idProducto}")
public ResponseEntity<Map<String, Object>> eliminarProducto(@PathVariable int idProducto) {
    ProductoDTO dto = productoService.eliminarProducto(idProducto);

    Map<String, Object> respuesta = new HashMap<>();
    respuesta.put("mensaje", "Producto eliminado correctamente");
    respuesta.put("producto", dto);

    return ResponseEntity.ok(respuesta);
}

}
