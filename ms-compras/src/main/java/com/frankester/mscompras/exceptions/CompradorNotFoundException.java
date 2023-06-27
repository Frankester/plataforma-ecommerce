package com.frankester.mscompras.exceptions;

public class CompradorNotFoundException extends Exception{
    public CompradorNotFoundException() {
        super("El comprador solicitado no existe");
    }

    public CompradorNotFoundException(String message) {
        super(message);
    }

    public CompradorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CompradorNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CompradorNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
