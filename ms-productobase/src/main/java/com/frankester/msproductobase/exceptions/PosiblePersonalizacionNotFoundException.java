package com.frankester.msproductobase.exceptions;

public class PosiblePersonalizacionNotFoundException extends Exception {

   
    public PosiblePersonalizacionNotFoundException(String message) {
        super(message);
    }

    public PosiblePersonalizacionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PosiblePersonalizacionNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PosiblePersonalizacionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
