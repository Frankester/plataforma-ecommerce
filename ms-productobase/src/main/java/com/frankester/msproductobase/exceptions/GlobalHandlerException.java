package com.frankester.msproductobase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ProductoBaseNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String productoBaseNotFoundException(ProductoBaseNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(PosiblePersonalizacionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String posiblePersonalizacionNotFoundException(PosiblePersonalizacionNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(PosiblePersonalizacionExistenteException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String posiblePersonalizacionExistenteException(PosiblePersonalizacionExistenteException ex){
        return ex.getLocalizedMessage();
    }
}
