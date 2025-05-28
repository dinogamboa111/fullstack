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
    public ResponseEntity<ClienteDTO> crear(/* @Valid */ @RequestBody ClienteDTO dto /* , BindingResult result */) {

        /*
         * if (result.hasErrors()) { // si capta un error de validacion procede con el
         * if
         * String errores = result.getFieldErrors().stream() //obtiene todos los errores
         * y los pasa a stream
         * .map(e -> e.getField() + ": " + e.getDefaultMessage()) // aqui los convierte
         * a un string, ejemplo : email: debe ser correo valido
         * .collect(Collectors.joining("; ")); // une cada error que esta y te arma un
         * solo string , los separa con ;
         * throw new DatosInvalidosException(errores); //lanza mi exception, pueden ver
         * la exception declarada en GlobalException y tiene su propia clase igual que
         * lleva el "metodo"
         * }
         */

        ClienteDTO creado = clienteService.crearCliente(dto); // si no hay errores desp de todo, llama al servicio
                                                              // "crearCliente" quien lo guarda en la bbdd, pueden ver
                                                              // el metodo que contiene validaciones propias en SERVICE
        return ResponseEntity.status(HttpStatus.CREATED).body(creado); // retorna un gttp 201 que significa que se creo,
                                                                       // y
                                                                       // pasa el dto creado en el body, osea en postman
                                                                       // saldra el body que se logro crear, igual si lo
                                                                       // prueban se inserta en la bbdd directo
    }

    // @PostMapping
    // public ResponseEntity<ClienteDTO> crearCliente(@RequestBody ClienteDTO
    // clienteDTO) {
    // try {
    // // Llamada al servicio para crear el cliente
    // ClienteDTO nuevoCliente = clienteService.crearCliente(clienteDTO);
    // return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    // } catch (CorreoDuplicadoException e) {
    // // Manejo de error para correo duplicado
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body(new ClienteDTO()); // Podrías devolver el mensaje de error si es
    // necesario
    // } catch (Exception e) {
    // // Manejo general de errores internos
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body(null); // Podrías incluir un mensaje más descriptivo aquí si lo deseas
    // }
    // }

    @GetMapping("/{rut}")
    public ResponseEntity<ClienteDTO> obtener(@PathVariable int rut) {
        ClienteDTO cliente = clienteService.obtenerCliente(rut);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listar() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    @PutMapping("/{rut}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable int rut,
            @RequestBody ClienteDTO dto) {

        ClienteDTO actualizado = clienteService.actualizarCliente(rut, dto);
        // se llama al service para actualizar
        // Todas las validacione estan  manejadas dentro del service.

        return ResponseEntity.ok(actualizado); // Devuelve HTTP 200 con el cliente actualizado en el body.
    }

    // @PutMapping("/{rut}")
    // public ResponseEntity<ClienteDTO> actualizar(@PathVariable int rut,
    // @Valid @RequestBody ClienteDTO dto,
    // BindingResult result) {
    // if (result.hasErrors()) {
    // String errores = result.getFieldErrors().stream() // obtiene todos los
    // errores
    // .map(e -> e.getField() + ": " + e.getDefaultMessage()) // transforma cada
    // error en un string ej:
    // // email: no puede estar vacio, esto segun
    // // los parametros que usamos como notNull
    // // Email etc
    // .collect(Collectors.joining("; ")); // une todos los errores en un solo
    // string separado por el ;
    // throw new DatosInvalidosException(errores); // lanza nuestro exception
    // programada en ese metodo
    // }

    // ClienteDTO actualizado = clienteService.actualizarCliente(rut, dto); // SI NO
    // EXISTIERON ERRORES DE VALIDACION,
    // // osea se encontro el rut, se llama al
    // // SERVICE para actualizar,
    // // esta actualizacion se guarda finalmente
    // // en la bbdd, el save se ejecuta en el
    // // metodo declarado en el SERVICE
    // // **RECORDAR QUE EL METODO EN SERVICE
    // // TIENE TAMBIEN
    // // CONTROL DE ERRORES, COMO PK DUPLICADA, O
    // // MAIL DUPLICADO.

    // return ResponseEntity.ok(actualizado); // aqui nos devuelve el exito con un
    // codigo 200 http
    // }

    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> eliminar(@PathVariable int rut) {
        clienteService.eliminarCliente(rut);
        return ResponseEntity.noContent().build();
    }
}