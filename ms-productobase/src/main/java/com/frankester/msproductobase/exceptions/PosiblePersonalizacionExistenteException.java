package com.frankester.msproductobase.exceptions;

public class PosiblePersonalizacionExistenteException extends Exception {
    public PosiblePersonalizacionExistenteException(String message) {
        super(message);
    }

    public PosiblePersonalizacionExistenteException(String message, Throwable cause) {
        super(message, cause);
    }

    public PosiblePersonalizacionExistenteException(Throwable cause) {
        super(cause);
    }

    protected PosiblePersonalizacionExistenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
