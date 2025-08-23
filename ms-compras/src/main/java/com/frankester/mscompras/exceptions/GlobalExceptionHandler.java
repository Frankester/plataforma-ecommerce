package com.frankester.mscompras.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CarritoDeCompraWithCompraException.class, EmptyItemsException.class, DifferentTiendaException.class, MedioDePagoAlreadyExistsException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequestsExceptionHandler(Exception ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler({CarritoDeCompraNotFoundException.class, CompradorNotFoundException.class,
            CompraNotFoundException.class, PublicacionNotFoundException.class,
            TiendaNotFoundException.class, VendedorNotFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundExceptionsHandler(Exception ex){
        return ex.getLocalizedMessage();
    }


    @ExceptionHandler({VendedorNotPermittedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String forbiddenExceptionsHandler(VendedorNotPermittedException ex){
        return ex.getLocalizedMessage();
    }



}
