package com.frankester.mscompras.controllers;

import com.frankester.mscompras.exceptions.*;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.compra.CarritoDeCompra;
import com.frankester.mscompras.models.compra.Item;
import com.frankester.mscompras.models.dto.CarritoDeCompraDTO;
import com.frankester.mscompras.models.dto.CarritoResponse;
import com.frankester.mscompras.models.dto.ItemDTO;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.repositories.RepoCarritoDeCompra;
import com.frankester.mscompras.repositories.RepoCompradores;
import com.frankester.mscompras.repositories.RepoPublicaciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RepositoryRestController(path="carritos")
public class CarritoDeCompraControllerComplement {


    @Autowired
    RepoCarritoDeCompra repo;

    @Autowired
    RepoCompradores repoCompradores;

    @Autowired
    RepoPublicaciones repoPublicaciones;

    @PostMapping
    public ResponseEntity<Object> createCarrito(
            @RequestBody CarritoDeCompraDTO nuevoCarrito
    ) throws CompradorNotFoundException, PublicacionNotFoundException, DifferentTiendaException, EmptyItemsException, CarritoDeCompraWithCompraException {

        Optional<Comprador> compradorOP = repoCompradores.findById(nuevoCarrito.getCompradorId());

        if(compradorOP.isEmpty()){
            throw new CompradorNotFoundException();
        }

        Comprador comprador = compradorOP.get();

        List<ItemDTO> itemsDTO = nuevoCarrito.getItems();

        long itemsWithValidPublicacion =  itemsDTO.stream().filter(item ->
            repoPublicaciones.existsById(item.getIdPublicacion())
        ).count();

        if(itemsWithValidPublicacion != itemsDTO.size()){
            throw new PublicacionNotFoundException("Algunos items tienen publicaciones que no existen");
        }

        CarritoDeCompra carrito = new CarritoDeCompra();
        carrito.setComprador(comprador);

        for(ItemDTO itemDTO: itemsDTO){
            Item nuevoItem = new Item();
            nuevoItem.setCantidad(itemDTO.getCantidad());

            Optional<Publicacion> publicacionOP = repoPublicaciones.findById(itemDTO.getIdPublicacion());

            Publicacion publicacion = publicacionOP.get();

            nuevoItem.setPublicacion(publicacion);

            carrito.agregarItem(nuevoItem);
        }

        repo.save(carrito);

        CarritoResponse carritoRes = new CarritoResponse();
        carritoRes.setPrecioCarrito(carrito.calcularPrecioTotal());
        carritoRes.setItems(nuevoCarrito.getItems());
        carritoRes.setCompradorId(nuevoCarrito.getCompradorId());

        return ResponseEntity.ok(carritoRes);
    }

    @PutMapping(path = "/{idCarrito}")
    public ResponseEntity<Object> actualizarCarrito(
            @RequestBody CarritoDeCompraDTO nuevoCarrito,
            @PathVariable Long idCarrito
    ) throws CarritoDeCompraNotFoundException, CompradorNotFoundException, PublicacionNotFoundException, DifferentTiendaException, CarritoDeCompraWithCompraException {
        Optional<CarritoDeCompra> carritoOP = repo.findById(idCarrito);

        if(carritoOP.isEmpty()){
            throw new CarritoDeCompraNotFoundException();
        }


        CarritoDeCompra carrito = carritoOP.get();

        if(!carrito.isActivo()){
            throw new CarritoDeCompraNotFoundException();
        }

        Optional<Comprador> compradorOP = repoCompradores.findById(nuevoCarrito.getCompradorId());

        if(compradorOP.isEmpty()){
            throw new CompradorNotFoundException();
        }

        Comprador comprador = compradorOP.get();

        if(!comprador.isActivo()){
            throw new CompradorNotFoundException();
        }

        List<ItemDTO> itemsDTO = nuevoCarrito.getItems();

        long itemsWithValidPublicacion =  itemsDTO.stream().filter(item ->
                repoPublicaciones.existsById(item.getIdPublicacion())
        ).count();

        if(itemsWithValidPublicacion != itemsDTO.size()){
            throw new PublicacionNotFoundException("Algunos items tienen publicaciones que no existen");
        }

        carrito.setComprador(comprador);

        if(!carrito.getItems().isEmpty()){
            carrito.setItems(new ArrayList<>());
        }

        for(ItemDTO itemDTO: itemsDTO){
            Item nuevoItem = new Item();
            nuevoItem.setCantidad(itemDTO.getCantidad());

            Optional<Publicacion> publicacionOP = repoPublicaciones.findById(itemDTO.getIdPublicacion());

            Publicacion publicacion = publicacionOP.get();

            nuevoItem.setPublicacion(publicacion);

            carrito.agregarItem(nuevoItem);
        }

        repo.save(carrito);

        return ResponseEntity.ok(nuevoCarrito);
    }


    @DeleteMapping(path="/{idCarrito}")
    public ResponseEntity<Object> borradoLogicoCarrito(
            @PathVariable Long idCarrito
    )throws CarritoDeCompraNotFoundException {
        Optional<CarritoDeCompra> carritoOp = repo.findById(idCarrito);

        if(carritoOp.isEmpty()){
            throw new CarritoDeCompraNotFoundException();
        }

        CarritoDeCompra carrito = carritoOp.get();

        boolean esActivo = carrito.isActivo();

        if(!esActivo){
            throw new CarritoDeCompraNotFoundException();
        }

        carrito.setActivo(false);
        repo.save(carrito);

        return ResponseEntity.ok("Carrito se compra eliminado correctamente");
    }

}
