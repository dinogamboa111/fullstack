package cl.fullstack.pedido_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PedidoDTO> getAllUsuarios() {
        return pedidoService.getAllPedidos();
    }

    @GetMapping("/{idPedido}")
    public PedidoDTO getUsuarioById(@PathVariable int idPedido) {
        return pedidoService.getPedidoById(idPedido);
    }

    @PostMapping
    public PedidoDTO createUsuario(@RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.createPedido(pedidoDTO);
    }

    @PutMapping("/{idPedido}")
    public PedidoDTO updateUsuario(@PathVariable int idPedido, @RequestBody PedidoDTO pedidoDTO) {
        return pedidoService.updatePedido(idPedido, pedidoDTO);
    }

    @DeleteMapping("/{idPedido}")
    public void deleteUsuario(@PathVariable int idPedido) {
        pedidoService.deletePedido(idPedido);
    }

}
