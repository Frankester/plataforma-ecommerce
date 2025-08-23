package com.frankester.mscompras.models.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class CompraDTO {

    private String tipoMoneda;
    private Long compradorId;
    private Long carritoDeCompraId;

}
