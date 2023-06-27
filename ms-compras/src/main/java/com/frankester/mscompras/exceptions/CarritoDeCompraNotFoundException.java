package com.frankester.mscompras.exceptions;

public class CarritoDeCompraNotFoundException extends Exception{

    public CarritoDeCompraNotFoundException() {
        super("El carrito de compra solicitado no existe");
    }

    public CarritoDeCompraNotFoundException(String message) {
        super(message);
    }

    public CarritoDeCompraNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CarritoDeCompraNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CarritoDeCompraNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
