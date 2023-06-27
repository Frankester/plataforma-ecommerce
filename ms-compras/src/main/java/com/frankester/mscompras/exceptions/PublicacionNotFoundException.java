package com.frankester.mscompras.exceptions;

public class PublicacionNotFoundException extends Exception{

    public PublicacionNotFoundException() {
        super("La publicación solicitada no existe");
    }

    public PublicacionNotFoundException(String message) {
        super(message);
    }

    public PublicacionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PublicacionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PublicacionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
