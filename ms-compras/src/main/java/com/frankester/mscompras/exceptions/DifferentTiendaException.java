package com.frankester.mscompras.exceptions;

public class DifferentTiendaException extends Exception{

    public DifferentTiendaException(String message) {
        super(message);
    }

    public DifferentTiendaException(String message, Throwable cause) {
        super(message, cause);
    }

    public DifferentTiendaException(Throwable cause) {
        super(cause);
    }

    protected DifferentTiendaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
