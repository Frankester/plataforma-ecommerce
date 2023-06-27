package com.frankester.mscompras.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(CarritoDeCompraNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String carritoDeCompraNotFoundException(CarritoDeCompraNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(CarritoDeCompraWithCompraException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String carritoDeCompraWithCompraException(CarritoDeCompraWithCompraException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(EmptyItemsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String carritoWithEmptyItemsException(EmptyItemsException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(CompradorNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String compradorNotFoundException(CompradorNotFoundException ex){
        return ex.getLocalizedMessage();
    }


    @ExceptionHandler(CompraNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String compraNotFoundException(CompraNotFoundException ex){
        return ex.getLocalizedMessage();
    }


    @ExceptionHandler(PublicacionNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String publicacionNotFoundException(PublicacionNotFoundException ex){
        return ex.getLocalizedMessage();
    }
    @ExceptionHandler(TiendaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String tiendaNotFoundException(TiendaNotFoundException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(DifferentTiendaException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String differentTiendaException(DifferentTiendaException ex){
        return ex.getLocalizedMessage();
    }


    @ExceptionHandler(VendedorNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String vendedorNotFoundException(VendedorNotFoundException ex){
        return ex.getLocalizedMessage();
    }


    @ExceptionHandler(VendedorNotPermittedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String tiendaNotFoundException(VendedorNotPermittedException ex){
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(MedioDePagoAlreadyExistsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String medioDePagoAlreadyExistsException(MedioDePagoAlreadyExistsException ex){
        return ex.getLocalizedMessage();
    }


}
