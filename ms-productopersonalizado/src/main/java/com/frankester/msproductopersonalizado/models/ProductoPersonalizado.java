package com.frankester.msproductopersonalizado.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class ProductoPersonalizado {
    @Id
    @GeneratedValue
    private Long id;

    private String nombreProductoBase;
    private String cuilVendedor;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_personalizacion", referencedColumnName = "id")
    private Personalizacion personalizacion;

    private BigDecimal precio;

    private boolean activo;

    public ProductoPersonalizado(){
        this.activo = true;
    }


}
