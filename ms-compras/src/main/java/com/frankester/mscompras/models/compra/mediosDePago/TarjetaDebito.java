package com.frankester.mscompras.models.compra.mediosDePago;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TarjetaDebito extends MedioDePago {

    private String numeroTarjeta;
    private String codigoDeSeguridad;
    private String nombreTitular;
    private String apellidoTitular;
    private String tipoDeTarjeta;

    @Override
    public void pagar() {
        //TODO pagar compra
    }
}
