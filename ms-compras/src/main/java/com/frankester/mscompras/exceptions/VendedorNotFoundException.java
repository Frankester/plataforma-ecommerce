package com.frankester.mscompras.exceptions;

public class VendedorNotFoundException extends Exception{

    public VendedorNotFoundException() {
        super("El vendedor solicitado no existe");
    }
    public VendedorNotFoundException(String message) {
        super(message);
    }

    public VendedorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public VendedorNotFoundException(Throwable cause) {
        super(cause);
    }

    protected VendedorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
