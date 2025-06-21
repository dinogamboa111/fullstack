package cl.fullstack.ubicacion_ms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class IntegridadDatosException extends RuntimeException {
    public IntegridadDatosException(String mensaje) {
        super(mensaje);
    }
}