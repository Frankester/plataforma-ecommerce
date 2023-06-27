package com.frankester.mscompras.exceptions;

public class MedioDePagoAlreadyExistsException extends  Exception{

    public MedioDePagoAlreadyExistsException(String message) {
        super(message);
    }

    public MedioDePagoAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MedioDePagoAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected MedioDePagoAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
