package com.frankester.msproductopersonalizado.exceptions;

public class ProductoPersonalizadoNotFoundException extends Exception{

    public ProductoPersonalizadoNotFoundException() {
        super("El producto personalizado solicitado no existe");
    }

    public ProductoPersonalizadoNotFoundException(String message) {
        super(message);
    }

    public ProductoPersonalizadoNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductoPersonalizadoNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProductoPersonalizadoNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
