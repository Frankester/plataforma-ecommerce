package com.frankester.msproductopersonalizado.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Personalizacion {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private BigDecimal precio;
    private String contenido;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_posible_personalizacion", referencedColumnName = "id")
    private PosiblePersonalizacion posiblePersonalizacion;
}
