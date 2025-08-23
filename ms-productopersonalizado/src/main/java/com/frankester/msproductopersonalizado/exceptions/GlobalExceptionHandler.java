package com.frankester.msproductopersonalizado.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ProductoBaseNotFoundException.class, PosiblePersonalizacionNotFoundException.class, ProductoPersonalizadoNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productoBaseNotFoundException(Exception ex){
        return ex.getLocalizedMessage();
    }


}
