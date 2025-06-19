package cl.fullstack.ubicacion_ms.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
        return buildErrorResponse("Recurso no encontrado", ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> handleRecursoDuplicadoException(RecursoDuplicadoException ex) {
        return buildErrorResponse("Recurso duplicado", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IntegridadDatosException.class)
    public ResponseEntity<Map<String, String>> handleIntegridadDatosException(IntegridadDatosException ex) {
        return buildErrorResponse("Error de integridad", ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse("Solicitud invalida", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Metodo auxiliar para respuestas consistentes
    private ResponseEntity<Map<String, String>> buildErrorResponse(String error, String mensaje, HttpStatus status) {
        Map<String, String> response = new HashMap<>();
        response.put("error", error);
        response.put("mensaje", mensaje);
        return new ResponseEntity<>(response, status);
    }
//  aca manejo directamente  DataIntegrityViolationException porque al hacer commit se produce un desface en la base
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        return buildErrorResponse("Error de integridad", 
                                "Operacion no permitida por restricciones de base de datos", 
                                HttpStatus.CONFLICT);
    }

}