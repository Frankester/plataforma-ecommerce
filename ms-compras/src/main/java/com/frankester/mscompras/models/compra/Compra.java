package com.frankester.mscompras.models.compra;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.estados.EstadoCompra;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Compra extends Persistence {

    private LocalTime horaInicio;
    private LocalDate fechaInicio;

    @Enumerated(EnumType.STRING)
    private EstadoCompra estadoCompra;

    @ElementCollection
    @CollectionTable(name = "compra_fecha_cambio_estado",
        joinColumns = @JoinColumn(name = "id_compra", referencedColumnName = "id"))
    @Column(name = "fecha_cambio_estado")
    private List<LocalDate> fechasCambioDeEstado;

    @OneToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "id_compra", referencedColumnName= "id")
    private FormaDePago formaPago;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="id_carrito_compra", referencedColumnName = "id")
    private CarritoDeCompra carrito;

    private boolean activo;

    public Compra(){
        this.fechasCambioDeEstado = new ArrayList<>();

        this.estadoCompra = EstadoCompra.PENDIENTE;
        this.fechaInicio = LocalDate.now();
        this.horaInicio = LocalTime.now();

        this.activo = true;
    }

    public void agregarFechaCambioDeEstado(LocalDate fechaCambioDeEstado){
        this.fechasCambioDeEstado.add(fechaCambioDeEstado);
    }
}
