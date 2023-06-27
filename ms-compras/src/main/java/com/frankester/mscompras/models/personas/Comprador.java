package com.frankester.mscompras.models.personas;

import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.compra.CarritoDeCompra;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Comprador extends Persistence {

    private String nombre;
    private String apellido;
    private String telefono;

    @OneToMany( mappedBy = "comprador")
    private List<CarritoDeCompra> carritos;

    private boolean activo;

    public Comprador(){
        this.carritos = new ArrayList<>();
        this.activo = true;
    }

    public void agregarCarrito(CarritoDeCompra nuevoCarrito){

        nuevoCarrito.setComprador(this);

        this.carritos.add(nuevoCarrito);
    }

}
