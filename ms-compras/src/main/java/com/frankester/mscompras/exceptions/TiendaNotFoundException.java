package com.frankester.mscompras.exceptions;

public class TiendaNotFoundException extends Exception{

    public TiendaNotFoundException() {
        super("La tienda solicitada no existe");
    }

    public TiendaNotFoundException(String message) {
        super(message);
    }

    public TiendaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TiendaNotFoundException(Throwable cause) {
        super(cause);
    }

    protected TiendaNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
