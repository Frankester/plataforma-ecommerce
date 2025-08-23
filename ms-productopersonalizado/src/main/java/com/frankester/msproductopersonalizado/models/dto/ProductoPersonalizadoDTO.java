package com.frankester.msproductopersonalizado.models.dto;

import com.frankester.msproductopersonalizado.models.PosiblePersonalizacion;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoPersonalizadoDTO {
    private String productoBaseNombre;
    private String nombre;
    private BigDecimal precio;
    private String contenido;
    private String vendedorCuil;
    private PosiblePersonalizacion posiblePersonalizacion;

}
