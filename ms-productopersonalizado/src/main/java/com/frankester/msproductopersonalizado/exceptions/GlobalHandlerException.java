package com.frankester.msproductopersonalizado.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ProductoBaseNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productoBaseNotFoundException(ProductoBaseNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(PosiblePersonalizacionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String posiblePersonalizacionNotFoundException(PosiblePersonalizacionNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(ProductoPersonalizadoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productoPersonalizadoNotFoundException(ProductoPersonalizadoNotFoundException ex){
        return ex.getLocalizedMessage();
    }


}
