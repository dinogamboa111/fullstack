package cl.fullstack.cliente_ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.fullstack.cliente_ms.dto.ClienteDTO;
import cl.fullstack.cliente_ms.service.IClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.crearCliente(dto));
    }

    @GetMapping("/{rut}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable int rut) {
        return ResponseEntity.ok(clienteService.obtenerCliente(rut));
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PutMapping("/{rut}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable int rut, @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(clienteService.actualizarCliente(rut, dto));
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable int rut) {
        clienteService.eliminarCliente(rut);
        return ResponseEntity.noContent().build();
    }
}