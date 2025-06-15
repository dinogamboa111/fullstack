package cl.fullstack.pruducto_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.fullstack.pruducto_ms.dto.ProductoDTO;
import cl.fullstack.pruducto_ms.service.IProductoService;
import java.util.List;


@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private IProductoService productoService;

    // crear producto
    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.createProducto(productoDTO));
    }

    // eliminar producto
    @DeleteMapping("/{idProducto}")
    public ResponseEntity<String> eliminar(@PathVariable int idProducto) {
        String mensaje = productoService.eliminarProducto(idProducto);
        return ResponseEntity.ok(mensaje);
    }

    // modificar producto
    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable int idProducto,
            @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.actualizarProducto(idProducto, productoDTO));
    }

    // obtener producto
    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> obtenerProducto(@PathVariable int idProducto) {
        return ResponseEntity.ok(productoService.obtenerProducto(idProducto));
    }

    // listar producto
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }


}
