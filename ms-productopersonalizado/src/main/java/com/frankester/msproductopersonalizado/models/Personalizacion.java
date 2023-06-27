package com.frankester.msproductopersonalizado.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Personalizacion {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private Integer precio;
    private String contenido;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_posible_personalizacion", referencedColumnName = "id")
    private PosiblePersonalizacion posiblePersonalizacion;
}
