package com.frankester.msproductopersonalizado.models.dto;


import com.frankester.msproductopersonalizado.models.PosiblePersonalizacion;
import lombok.Data;

@Data
public class ProductoBaseDTO {
    private String nombre;
    private PosiblePersonalizacion posiblePersonalizacion;
    
}
