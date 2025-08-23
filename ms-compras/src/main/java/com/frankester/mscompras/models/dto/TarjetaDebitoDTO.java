package com.frankester.mscompras.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TarjetaDebitoDTO extends CompraDTO {

    private String numeroTarjeta;
    private String codigoDeSeguridad;
    private String tipoDeTarjeta;
    private String nombreTitular;
    private String apellidoTitular;
}
