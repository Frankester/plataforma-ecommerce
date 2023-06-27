package com.frankester.mscompras.exceptions;

public class CompraNotFoundException extends Exception{

    public CompraNotFoundException() {
        super("La compra solicitada no existe");
    }

    public CompraNotFoundException(String message) {
        super(message);
    }

    public CompraNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompraNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CompraNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
