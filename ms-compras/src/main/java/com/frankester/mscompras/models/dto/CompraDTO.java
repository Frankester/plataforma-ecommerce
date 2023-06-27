package com.frankester.mscompras.models.dto;

import lombok.Data;

@Data
public class CompraDTO {

    private String tipoMoneda;
    private String numeroTarjeta;
    private String codigoDeSeguridad;
    private Long compradorId;
    private String medioDePago;
    private Long carritoDeCompraId;

}
