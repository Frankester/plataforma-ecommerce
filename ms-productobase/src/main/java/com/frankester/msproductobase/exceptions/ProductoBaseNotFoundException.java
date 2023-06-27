package com.frankester.msproductobase.exceptions;

import lombok.Getter;
import lombok.Setter;

public class ProductoBaseNotFoundException extends Exception{

    @Getter
    @Setter
    private Long idProductoBase;

    public ProductoBaseNotFoundException(String message, Long idProductoBase) {
        super(message);
        this.idProductoBase = idProductoBase;
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
