package cl.fullstack.pedido_ms.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.fullstack.pedido_ms.client.ProductoClient;
import cl.fullstack.pedido_ms.dto.PedidoDTO;
import cl.fullstack.pedido_ms.dto.external.ProductoDTO;
import cl.fullstack.pedido_ms.entity.PedidoEntity;
import cl.fullstack.pedido_ms.service.IPedidoService;

import java.util.List;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private IPedidoService pedidoService;
    @Autowired
    private ModelMapper modelMapper;

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
public ResponseEntity<String> deletePedido(@PathVariable int idPedido) {
    String mensaje = pedidoService.deletePedido(idPedido);
    return ResponseEntity.ok(mensaje);
}


    
    //METODO DE PRUEBA PARA COMPROBAR CONEXION CON PRODUCTO-MS TRAYENDO TODOS LOS PRODUCTOS DISPONIBLES EN ESE MS
    @Autowired
    private ProductoClient productoClient;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarProductosDesdeProductoMs() {
        List<ProductoDTO> productos = productoClient.obtenerTodosLosProductos();
        return ResponseEntity.ok(productos);
    }


    //endpoint para conexion con centro-distribucion-ms
    @GetMapping("/despachador/{idDespachador}")
public ResponseEntity<List<PedidoDTO>> obtenerPedidosPorDespachador(@PathVariable Integer idDespachador) {
    List<PedidoEntity> pedidos = pedidoService.obtenerPedidosPorDespachador(idDespachador);
    List<PedidoDTO> pedidosDTO = pedidos.stream()
        .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
        .toList();
    return ResponseEntity.ok(pedidosDTO);
}

}