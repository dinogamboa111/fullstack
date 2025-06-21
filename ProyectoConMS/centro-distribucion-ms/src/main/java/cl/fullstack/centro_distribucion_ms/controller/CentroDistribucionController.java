package cl.fullstack.centro_distribucion_ms.controller;

import cl.fullstack.centro_distribucion_ms.dto.CentroDistribucionDTO;
import cl.fullstack.centro_distribucion_ms.entity.CentroDistribucionEntity;
import cl.fullstack.centro_distribucion_ms.service.ICentroDistribucionService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import java.util.List;

@RestController // indica que esta clase es un controlador rest
@RequestMapping("/api/centros-distribucion") // ruta base del controlador
public class CentroDistribucionController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ICentroDistribucionService centroDistribucionService; // inyecta el servicio

    @PostMapping
    public ResponseEntity<CentroDistribucionDTO> crearCentro(@RequestBody CentroDistribucionDTO dto) {
        // recibe el dto en el cuerpo de la peticion y lo guarda
        CentroDistribucionDTO creado = centroDistribucionService.crearCentroDistribucion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // responde con estado 201 y el dto creado
    }

    @GetMapping("/comuna/{idComuna}")
    public ResponseEntity<CentroDistribucionDTO> getCentroByComuna(@PathVariable int idComuna) {
        Optional<CentroDistribucionEntity> centroOpt = centroDistribucionService.findByComunaCubierta(idComuna);
        return centroOpt.map(centro -> ResponseEntity.ok(modelMapper.map(centro, CentroDistribucionDTO.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CentroDistribucionDTO>> obtenerTodos() {
        // obtiene todos los centros desde el servicio
        List<CentroDistribucionDTO> lista = centroDistribucionService.obtenerTodosLosCentros();
        return ResponseEntity.ok(lista); // responde con lista y estado 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroDistribucionDTO> obtenerPorId(@PathVariable int id) {
        // busca un centro por id
        CentroDistribucionDTO dto = centroDistribucionService.obtenerCentroDistribucionPorId(id);
        return ResponseEntity.ok(dto); // responde con dto y estado 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        // elimina el centro por id
        centroDistribucionService.eliminarCentroDistribucion(id);
        return ResponseEntity.noContent().build(); // responde sin contenido con estado 204
    }
}
