package cl.fullstack.usuario_ms.controller;

import cl.fullstack.usuario_ms.dto.UsuarioDTO;
import cl.fullstack.usuario_ms.exception.RecursoNoEncontradoException;
import cl.fullstack.usuario_ms.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.getAllUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable int id) {
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.createUsuario(usuarioDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable int id, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.updateUsuario(id, usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/centro/{idCentro}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorCentro(@PathVariable Integer idCentro) {
        UsuarioDTO usuario = usuarioService.findPrimerUsuarioPorCentro(idCentro);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/despachador/centro/{idCentro}")
    public ResponseEntity<List<UsuarioDTO>> obtenerDespachadoresPorCentro(@PathVariable int idCentro) {
        List<UsuarioDTO> despachadores = usuarioService.buscarDespachadoresPorCentro(idCentro);

        if (despachadores.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(despachadores);
    }
    //metodo probando 
     @GetMapping("/usuario-id/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        UsuarioDTO usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario == null) {
            // Retornar 404 si no se encuentra el usuario
            throw new RecursoNoEncontradoException("Usuario no encontrado con id: " + id);
        }
        return ResponseEntity.ok(usuario);
    }





}