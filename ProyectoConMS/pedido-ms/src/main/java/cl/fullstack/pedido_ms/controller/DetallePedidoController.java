package cl.fullstack.pedido_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pedido_ms.dto.DetallePedidoDTO;
import cl.fullstack.pedido_ms.entity.DetallePedidoId;
import cl.fullstack.pedido_ms.service.IDetallePedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/detallePedidos")
public class DetallePedidoController {

    @Autowired
    private IDetallePedidoService detallePedidoService;

    @GetMapping
    public List<DetallePedidoDTO> getAllDetallePedidos() {
        return detallePedidoService.getAllDetallePedidos();
    }

    @GetMapping("/{pedidoId}/{productoId}")
    public DetallePedidoDTO getDetallePedidoById(@PathVariable int pedidoId, @PathVariable int productoId) {
        DetallePedidoId id = new DetallePedidoId(pedidoId, productoId);
        return detallePedidoService.getDetallePedidoById(id);
    }

    @PostMapping
    public DetallePedidoDTO createDetallePedido(@RequestBody DetallePedidoDTO detallePedidoDTO) {
        return detallePedidoService.createDetallePedido(detallePedidoDTO);
    }

    @PutMapping("/{pedidoId}/{productoId}")
    public DetallePedidoDTO updateDetallePedido(
            @PathVariable int pedidoId,
            @PathVariable int productoId,
            @RequestBody DetallePedidoDTO detallePedidoDTO) {

        DetallePedidoId id = new DetallePedidoId(pedidoId, productoId);
        return detallePedidoService.updateDetallePedido(id, detallePedidoDTO);
    }

    @DeleteMapping("/{pedidoId}/{productoId}")
    public void deleteDetallePedido(@PathVariable int pedidoId, @PathVariable int productoId) {
        DetallePedidoId id = new DetallePedidoId(pedidoId, productoId);
        detallePedidoService.deleteDetallePedido(id);
    }
}
