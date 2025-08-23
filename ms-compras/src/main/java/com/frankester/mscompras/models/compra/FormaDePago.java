package com.frankester.mscompras.models.compra;

import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.compra.mediosDePago.MedioDePago;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FormaDePago extends Persistence {

    private String tipoMoneda;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_medio_pago", referencedColumnName = "id")
    private MedioDePago medioDePago;


}
