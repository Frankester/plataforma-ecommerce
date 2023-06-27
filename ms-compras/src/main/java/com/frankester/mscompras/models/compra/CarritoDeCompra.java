package com.frankester.mscompras.models.compra;

import com.frankester.mscompras.exceptions.CarritoDeCompraWithCompraException;
import com.frankester.mscompras.exceptions.DifferentTiendaException;
import com.frankester.mscompras.exceptions.EmptyItemsException;
import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.personas.Comprador;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
@Entity
public class CarritoDeCompra extends Persistence {

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_carrito_compra", referencedColumnName="id")
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name="id_comprador", referencedColumnName= "id")
    private Comprador comprador;

    @OneToOne
    @JoinColumn(name="id_compra", referencedColumnName = "id")
    private Compra compra;

    private boolean activo;

    public CarritoDeCompra(){
        this.items = new ArrayList<>();
        this.activo = true;
    }

    public void agregarItem(Item nuevoItem) throws DifferentTiendaException, CarritoDeCompraWithCompraException{

        if(this.compra != null){
            throw new CarritoDeCompraWithCompraException("El carrito de compra ya tiene una compra en proceso");
        }

        if(!items.isEmpty()){
            Tienda tienda = items.get(0).getPublicacion().getTienda();

            boolean isSameStore = nuevoItem.getPublicacion().getTienda().equals(tienda);

            if(!isSameStore){
                throw new DifferentTiendaException("Todos los items deben pertenecer a publicaciones de la misma tienda");
            }
        }


        this.items.add(nuevoItem);
    }

    public Integer calcularPrecioTotal() throws EmptyItemsException{
        if(this.items.isEmpty()){
            throw new EmptyItemsException("El carrito de compra no posee items");
        }

        Integer precioFinal = 0;

        Iterator<Item> itemIterator = items.iterator();
        while(itemIterator.hasNext()){
            Item item = itemIterator.next();

            Integer precioPublicacion = item.getPublicacion().getPrecioPublicacion();
            precioFinal += precioPublicacion * item.getCantidad();
        }

        return precioFinal;
    }


}
