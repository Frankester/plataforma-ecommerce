package com.frankester.mscompras.exceptions;

public class VendedorNotPermittedException extends Exception{

    public VendedorNotPermittedException(String message) {
        super(message);
    }

    public VendedorNotPermittedException(String message, Throwable cause) {
        super(message, cause);
    }

    public VendedorNotPermittedException(Throwable cause) {
        super(cause);
    }

    protected VendedorNotPermittedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
