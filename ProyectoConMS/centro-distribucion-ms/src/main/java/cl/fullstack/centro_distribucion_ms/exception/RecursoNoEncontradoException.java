package cl.fullstack.centro_distribucion_ms.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // indica que esta excepcion genera un estado 404 not found automaticamente
public class RecursoNoEncontradoException extends RuntimeException { // clase que extiende runtime exception para manejar error personalizado

    public RecursoNoEncontradoException(String mensaje) { // constructor que recibe un mensaje de error
        super(mensaje); // envia el mensaje a la clase padre RuntimeException
    }
}