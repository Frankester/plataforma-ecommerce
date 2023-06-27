package com.frankester.mscompras.exceptions;

public class EmptyItemsException extends Exception{

    public EmptyItemsException(String message) {
        super(message);
    }

    public EmptyItemsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyItemsException(Throwable cause) {
        super(cause);
    }

    protected EmptyItemsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
