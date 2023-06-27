package com.frankester.mscompras.models.dto;

import lombok.Data;

@Data
public class PublicacionDTO {

    private Long tiendaId;
    private String nombreProductoBase;
    private String nombrePersonalizacion;
    private String nombrePublicacion;
}
