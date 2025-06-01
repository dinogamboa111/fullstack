package cl.fullstack.centro_distribucion_ms.controller;

import cl.fullstack.centro_distribucion_ms.dto.ComunaCubiertaDTO;
import cl.fullstack.centro_distribucion_ms.service.IComunaCubiertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // define esta clase como controlador rest
@RequestMapping("api/comunas-cubiertas") // ruta base del controlador
public class ComunaCubiertaController {

    @Autowired
    private IComunaCubiertaService comunaCubiertaService; // inyecta el servicio de comuna cubierta

    // endpoint post para crear una nueva comuna cubierta
    @PostMapping
    public ResponseEntity<ComunaCubiertaDTO> crear(@RequestBody ComunaCubiertaDTO dto) {
        // llama al servicio para crear la comuna cubierta
        ComunaCubiertaDTO creado = comunaCubiertaService.crearComunaCubierta(dto);
        // devuelve la respuesta con estado created y el dto creado
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // endpoint get para obtener todas las comunas cubiertas
    @GetMapping
    public ResponseEntity<List<ComunaCubiertaDTO>> obtenerTodas() {
        // llama al servicio para obtener la lista
        List<ComunaCubiertaDTO> lista = comunaCubiertaService.obtenerTodas();
        // devuelve la lista con estado ok
        return ResponseEntity.ok(lista);
    }

    // // endpoint get para buscar una comuna cubierta por idcentro e idcomuna
    // @GetMapping("/buscar")
    // public ResponseEntity<ComunaCubiertaDTO> obtenerPorId(
    //         @RequestParam int idCentro,
    //         @RequestParam int idComuna) {
    //     // llama al servicio para obtener una comuna cubierta especifica
    //     ComunaCubiertaDTO dto = comunaCubiertaService.obtenerPorId(idCentro, idComuna);
    //     // devuelve el dto con estado ok
    //     return ResponseEntity.ok(dto);
    // }

    // endpoint delete para eliminar una comuna cubierta por body json con idcentro e idcomuna
    @DeleteMapping
    public ResponseEntity<Void> eliminar(@RequestBody ComunaCubiertaDTO dto) {
        // llama al servicio para eliminar la comuna cubierta
        comunaCubiertaService.eliminarPorId(dto.getIdCentro(), dto.getIdComuna());
        // devuelve respuesta sin contenido
        return ResponseEntity.noContent().build();
    }


    // endpoint get para obtener todas las comunas cubiertas de un centro específico
    @GetMapping("/por-centro/{idCentro}")  //api/comunas-cubiertas/por-cento
    public ResponseEntity<List<ComunaCubiertaDTO>> getByCentro(@PathVariable int idCentro) {
        // llama al servicio para obtener comunas por idCentro
        List<ComunaCubiertaDTO> resultado = comunaCubiertaService.obtenerPorIdCentro(idCentro);
        // devuelve la lista con estado ok
        return ResponseEntity.ok(resultado);
    }

    // endpoint get para obtener todas las ocurrencias de una comuna en diferentes centros
    @GetMapping("/por-comuna/{idComuna}")  //api/comunas-cubiertas/por-comuna
    public ResponseEntity<List<ComunaCubiertaDTO>> getByComuna(@PathVariable int idComuna) {
        // llama al servicio para obtener comunas por idComuna
        List<ComunaCubiertaDTO> resultado = comunaCubiertaService.obtenerPorIdComuna(idComuna);
        // devuelve la lista con estado ok
        return ResponseEntity.ok(resultado);
    }
}