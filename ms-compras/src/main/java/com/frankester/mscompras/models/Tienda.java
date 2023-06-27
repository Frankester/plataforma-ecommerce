package com.frankester.mscompras.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frankester.mscompras.models.compra.CarritoDeCompra;
import com.frankester.mscompras.models.personas.Vendedor;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Tienda extends Persistence {

    @Column(name="nombre")
    private String nombreTienda;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_vendedor", referencedColumnName= "id")
    private Vendedor vendedor;

    @OneToMany
    @JoinColumn(name = "id_tienda", referencedColumnName = "id")
    private List<Publicacion> publicaciones;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tienda", referencedColumnName = "id")
    private List<CarritoDeCompra> carritoDeCompra;

    private boolean activo;

    public Tienda (){
        this.publicaciones = new ArrayList<>();
        this.carritoDeCompra = new ArrayList<>();
        this.activo = true;
    }

    public void agregarCarritoDeCompra(CarritoDeCompra carrito){

        this.carritoDeCompra.add(carrito);
    }

    public void agregarPublicacion(Publicacion publicacion){

        this.publicaciones.add(publicacion);
    }
}
