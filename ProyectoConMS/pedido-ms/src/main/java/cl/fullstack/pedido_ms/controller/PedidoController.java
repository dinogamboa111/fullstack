package cl.fullstack.pedido_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pedido_ms.dto.PedidoDTO;
import cl.fullstack.pedido_ms.service.IPedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<PedidoDTO>> getAllPedidos() {
        return ResponseEntity.ok(pedidoService.getAllPedidos());
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable int idPedido) {
        return ResponseEntity.ok(pedidoService.getPedidoById(idPedido));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.createPedido(pedidoDTO));
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable int idPedido, @RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.updatePedido(idPedido, pedidoDTO));
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> deletePedido(@PathVariable int idPedido) {
        pedidoService.deletePedido(idPedido);
        return ResponseEntity.noContent().build();
    }
}