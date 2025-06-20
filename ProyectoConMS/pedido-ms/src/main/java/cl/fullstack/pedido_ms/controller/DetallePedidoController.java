package cl.fullstack.pedido_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoKey;
import cl.fullstack.pedido_ms.service.IDetallePedidoService;

import java.util.List;



@RestController
@RequestMapping("/api/detallepedidos")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> getAllDetallePedidos() {
        return ResponseEntity.ok(detallePedidoService.getAllDetallePedidos());
    }

    @GetMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<DetallePedidoDTO> getDetallePedidoById(@PathVariable int pedidoId, @PathVariable int productoId) {
        DetallePedidoKey id = new DetallePedidoKey(pedidoId, productoId);
        return ResponseEntity.ok(detallePedidoService.getDetallePedidoById(id));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoDTO> createDetallePedido( @RequestBody DetallePedidoDTO detallePedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(detallePedidoService.createDetallePedido(detallePedidoDTO));
    }

    @PutMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<DetallePedidoDTO> updateDetallePedido(@PathVariable int pedidoId,@PathVariable int productoId, @RequestBody DetallePedidoDTO detallePedidoDTO) {

        // Validar que los IDs en path y body coincidan para evitar inconsistencias
        if (detallePedidoDTO.getPedidoId() != pedidoId || detallePedidoDTO.getProductoId() != productoId) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null); // o lanzar excepción personalizada para datos inválidos
        }

        DetallePedidoKey id = new DetallePedidoKey(pedidoId, productoId);
        return ResponseEntity.ok(detallePedidoService.updateDetallePedido(id, detallePedidoDTO));
    }

    @DeleteMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable int pedidoId, @PathVariable int productoId) {
        DetallePedidoKey id = new DetallePedidoKey(pedidoId, productoId);
        detallePedidoService.eliminarDetallePedido(id);
        return ResponseEntity.noContent().build();
    }
}
























































/* 
package cl.fullstack.pedido_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoKey;
import cl.fullstack.pedido_ms.service.IDetallePedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/detallePedidos")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detallePedidoService;

    @GetMapping
    public ResponseEntity<List<DetallePedidoDTO>> getAllDetallePedidos() {
        return ResponseEntity.ok(detallePedidoService.getAllDetallePedidos());
    }

    @GetMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<DetallePedidoDTO> getDetallePedidoById(@PathVariable int pedidoId, @PathVariable int productoId) {
        DetallePedidoKey id = new DetallePedidoKey(pedidoId, productoId);
        return ResponseEntity.ok(detallePedidoService.getDetallePedidoById(id));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoDTO> createDetallePedido(@RequestBody DetallePedidoDTO detallePedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(detallePedidoService.createDetallePedido(detallePedidoDTO));
    }

    @PutMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<DetallePedidoDTO> updateDetallePedido(
            @PathVariable int pedidoId,
            @PathVariable int productoId,
            @RequestBody DetallePedidoDTO detallePedidoDTO) {

        DetallePedidoKey id = new DetallePedidoKey(pedidoId, productoId);
        return ResponseEntity.ok(detallePedidoService.updateDetallePedido(id, detallePedidoDTO));
    }

    @DeleteMapping("/{pedidoId}/{productoId}")
    public ResponseEntity<Void> deleteDetallePedido(@PathVariable int pedidoId, @PathVariable int productoId) {
        DetallePedidoKey id = new DetallePedidoKey(pedidoId, productoId);
        detallePedidoService.deleteDetallePedido(id);
        return ResponseEntity.noContent().build();
    }
}
*/