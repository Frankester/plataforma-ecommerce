package com.frankester.mscompras.models.compra;

import com.frankester.mscompras.models.Persistence;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class FormaDePago extends Persistence {

    private String tipoMoneda;
    private String numeroTarjeta;
    private String codigoDeSeguridad;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_medio_pago", referencedColumnName = "id")
    private MedioDePago medioDePago;


}
