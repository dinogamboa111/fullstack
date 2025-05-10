package cl.fullstack.cliente_ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.fullstack.cliente_ms.dto.ClienteDTO;
import cl.fullstack.cliente_ms.exception.CorreoDuplicadoException;
import cl.fullstack.cliente_ms.service.IClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        try {
            // Llamada al servicio para crear el cliente
            ClienteDTO nuevoCliente = clienteService.crearCliente(clienteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
        } catch (CorreoDuplicadoException e) {
            // Manejo de error para correo duplicado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ClienteDTO());  // Podrías devolver el mensaje de error si es necesario
        } catch (Exception e) {
            // Manejo general de errores internos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Podrías incluir un mensaje más descriptivo aquí si lo deseas
        }
    }

      @GetMapping("/{rut}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable int rut) {
        try {
            ClienteDTO cliente = clienteService.obtenerCliente(rut);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null); 
        }
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PutMapping("/{rut}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable int rut, @Valid @RequestBody ClienteDTO dto,
            BindingResult result) {
        if (result.hasErrors()) {
            // Aquí manejas los errores de validación
            return ResponseEntity.badRequest().body(null); // O puedes devolver detalles de los errores
        }
        return ResponseEntity.ok(clienteService.actualizarCliente(rut, dto));
    }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable int rut) {
        clienteService.eliminarCliente(rut);
        return ResponseEntity.noContent().build();
    }
}