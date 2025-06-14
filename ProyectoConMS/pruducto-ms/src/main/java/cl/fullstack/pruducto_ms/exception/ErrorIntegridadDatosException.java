package cl.fullstack.pruducto_ms.exception;


public class ErrorIntegridadDatosException extends RuntimeException {

    public ErrorIntegridadDatosException(String mensaje) {
        super(mensaje);
    }

    public ErrorIntegridadDatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}