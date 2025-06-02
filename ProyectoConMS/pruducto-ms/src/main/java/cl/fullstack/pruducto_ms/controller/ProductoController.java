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

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable int idProducto) {
        return ResponseEntity.ok(productoService.getProductoById(idProducto));
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> createProducto(@RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.createProducto(productoDTO));
    }

    @PutMapping("/{idProducto}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable int idProducto, @RequestBody ProductoDTO productoDTO) {
        return ResponseEntity.ok(productoService.updateProducto(idProducto, productoDTO));
    }

    @DeleteMapping("/{idProducto}")
    public ResponseEntity<Void> deleteProducto(@PathVariable int idProducto) {
        productoService.deleteProducto(idProducto);
        return ResponseEntity.noContent().build();
    }
}
