package com.frankester.mscompras.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class CarritoDeCompraDTO {

    private Long compradorId;
    private List<ItemDTO> items;

}
