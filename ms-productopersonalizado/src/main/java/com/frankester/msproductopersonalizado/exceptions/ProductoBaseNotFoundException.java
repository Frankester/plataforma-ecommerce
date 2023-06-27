package com.frankester.msproductopersonalizado.exceptions;

import lombok.Getter;
import lombok.Setter;

public class ProductoBaseNotFoundException extends Exception{


    public ProductoBaseNotFoundException() {
        super("No existe un producto base con ese nombre");

    }

    public ProductoBaseNotFoundException(String message) {
        super(message);
    }

    public ProductoBaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductoBaseNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ProductoBaseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
