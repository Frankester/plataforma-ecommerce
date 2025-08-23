package com.frankester.msproductobase.models.dto;

import com.frankester.msproductobase.models.PosiblePersonalizacion;

import lombok.Data;

@Data
public class ProductoBaseDTO {
    private String nombre;
    private PosiblePersonalizacionDTO posiblePersonalizacion;
    
}
