package com.frankester.msproductobase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductoBaseNotFoundException.class, PosiblePersonalizacionNotFoundException.class, PosiblePersonalizacionExistenteException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleBadRequestExceptions(Exception ex){
        return ex.getLocalizedMessage();
    }

}
