package com.frankester.mscompras.models;

import com.frankester.mscompras.models.estados.EstadoPublicacion;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Publicacion extends Persistence {


    @Enumerated(EnumType.STRING)
    private EstadoPublicacion estado;

    private LocalDate fechaDePublicacion;

    private String nombrePublicacion;

    private String nombreProductoBase;
    private String nombrePersonalizacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tienda", referencedColumnName = "id")
    private Tienda tienda;

    private BigDecimal precioPublicacion;
    private boolean activo;


    public Publicacion(){
        this.estado = EstadoPublicacion.VIRGENTE;
        this.activo = true;
    }
}
