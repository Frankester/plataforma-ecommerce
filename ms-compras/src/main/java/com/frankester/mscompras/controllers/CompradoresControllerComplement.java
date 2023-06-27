package com.frankester.mscompras.controllers;

import com.frankester.mscompras.exceptions.*;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.compra.CarritoDeCompra;
import com.frankester.mscompras.models.compra.Compra;
import com.frankester.mscompras.models.compra.Item;
import com.frankester.mscompras.models.dto.CarritoResponse;
import com.frankester.mscompras.models.dto.ItemDTO;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.repositories.RepoCarritoDeCompra;
import com.frankester.mscompras.repositories.RepoCompradores;
import com.frankester.mscompras.repositories.RepoPublicaciones;
import com.frankester.mscompras.repositories.RepoTienda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RepositoryRestController(path = "compradores")
public class CompradoresControllerComplement {

    @Autowired
    RepoCompradores repo;

    @Autowired
    RepoTienda repoTienda;

    @Autowired
    RepoPublicaciones repoPublicaciones;

    @Autowired
    RepoCarritoDeCompra repoCarrito;


    @Transactional
    @PostMapping(path = "/{idComprador}/carritos/{idCarrito}")
    public ResponseEntity<Object> agregarItemCarrito(
            @RequestBody ItemDTO item,
            @PathVariable Long idComprador,
            @PathVariable Long idCarrito
    ) throws CompradorNotFoundException, PublicacionNotFoundException, CarritoDeCompraNotFoundException, DifferentTiendaException, CarritoDeCompraWithCompraException {
        Optional<Comprador> compradorOp = repo.findById(idComprador);

        if(compradorOp.isEmpty()){
            throw new CompradorNotFoundException();
        }

        Comprador comprador = compradorOp.get();

        if(!comprador.isActivo()){
            throw new CompradorNotFoundException();
        }

        Optional<CarritoDeCompra> carritoDeCompraOp = repoCarrito.findById(idCarrito);

        if(carritoDeCompraOp.isEmpty()){
            throw new CarritoDeCompraNotFoundException();
        }

        CarritoDeCompra carritoDeCompra = carritoDeCompraOp.get();

        if(!carritoDeCompra.isActivo()){
            throw new CarritoDeCompraNotFoundException();
        }

        Optional<Publicacion> publicacionOp = repoPublicaciones.findById(item.getIdPublicacion());

        if(publicacionOp.isEmpty()){
            throw new PublicacionNotFoundException();
        }

        Publicacion publicacion = publicacionOp.get();

        if(!publicacion.isActivo()){
            throw new PublicacionNotFoundException();
        }

        Tienda tienda = publicacion.getTienda();


        Item nuevoItem = new Item();
        nuevoItem.setPublicacion(publicacion);
        nuevoItem.setCantidad(item.getCantidad());


        carritoDeCompra.agregarItem(nuevoItem);

        comprador.agregarCarrito(carritoDeCompra);


        this.repoTienda.save(tienda);
        this.repo.save(comprador);


        return ResponseEntity.ok(item);
    }

    @DeleteMapping(path="/{idComprador}")
    public ResponseEntity<Object> borradoLogicoComprador(
            @PathVariable Long idComprador
    )throws CompradorNotFoundException {
        Optional<Comprador> compradorOp = repo.findById(idComprador);

        if(compradorOp.isEmpty()){
            throw new CompradorNotFoundException();
        }

        Comprador comprador = compradorOp.get();

        boolean esActivo = comprador.isActivo();

        if(!esActivo){
            throw new CompradorNotFoundException();
        }

        comprador.setActivo(false);
        repo.save(comprador);

        return ResponseEntity.ok("Comprador eliminado correctamente");
    }

}
