package cl.fullstack.cliente_ms.exception;

public class CorreoDuplicadoException extends RuntimeException {
    public CorreoDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
