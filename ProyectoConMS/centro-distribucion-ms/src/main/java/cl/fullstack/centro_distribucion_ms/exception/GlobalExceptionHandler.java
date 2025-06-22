package cl.fullstack.centro_distribucion_ms.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // indica que esta clase maneja excepciones en todos los controladores
public class GlobalExceptionHandler {

   @ExceptionHandler(RecursoNoEncontradoException.class) // atrapa excepciones de tipo RecursoNoEncontradoException
    public ResponseEntity<Map<String, String>> handleRecursoNoEncontrado(RecursoNoEncontradoException ex) {
        // crea un mapa para enviar detalles del error en la respuesta
        Map<String, String> error = new HashMap<>();
        // agrega un mensaje fijo para identificar el tipo de error
        error.put("error", "recurso no encontrado");
        // agrega el mensaje de la excepcion capturada para dar detalles
        error.put("mensaje", ex.getMessage());
        // devuelve la respuesta con el mapa y codigo http 404 not found
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    
    @ExceptionHandler(DatosInvalidosException.class)
    public ResponseEntity<Map<String, Object>> manejarDatosInvalidos(DatosInvalidosException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());
        error.put("fecha", LocalDateTime.now());
        error.put("codigo", HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> manejarRecursoDuplicado(RecursoDuplicadoException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("mensaje", ex.getMessage());
        error.put("fecha", LocalDateTime.now());
        error.put("codigo", HttpStatus.CONFLICT.value());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
