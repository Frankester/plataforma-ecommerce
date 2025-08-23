package com.frankester.mscompras.models.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarritoResponse {
    private Long compradorId;
    private List<ItemDTO> items;
    private BigDecimal precioCarrito;
}
