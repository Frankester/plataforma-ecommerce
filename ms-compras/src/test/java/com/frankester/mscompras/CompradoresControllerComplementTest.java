package com.frankester.mscompras;

import com.frankester.mscompras.controllers.CompradoresControllerComplement;
import com.frankester.mscompras.exceptions.*;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.compra.CarritoDeCompra;
import com.frankester.mscompras.models.compra.Item;
import com.frankester.mscompras.models.dto.ItemDTO;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.repositories.RepoCarritoDeCompra;
import com.frankester.mscompras.repositories.RepoCompradores;
import com.frankester.mscompras.repositories.RepoPublicaciones;
import com.frankester.mscompras.repositories.RepoTienda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class CompradoresControllerComplementTest {

    @Mock
    RepoCompradores repoCompradores;
    @Mock
    RepoTienda repoTienda;
    @Mock
    RepoPublicaciones repoPublicaciones;
    @Mock
    RepoCarritoDeCompra repoCarrito;

    CompradoresControllerComplement controller;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.controller = new CompradoresControllerComplement(repoCompradores,repoTienda, repoPublicaciones, repoCarrito);
    }

    @Test
    void sePuedeAgregarUnItemAlCarrito() throws CarritoDeCompraWithCompraException, DifferentTiendaException, PublicacionNotFoundException, CarritoDeCompraNotFoundException, CompradorNotFoundException {
        ItemDTO itemDTO = new ItemDTO();
        int cantidadItem = 12;
        itemDTO.setCantidad(cantidadItem);

        Long idPublicacion = 1L;

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");


        Publicacion publicacion = Utils.crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);
        publicacion.setId(idPublicacion);
        itemDTO.setIdPublicacion(idPublicacion);

        Long idComprador = 1L;
        Long idCarrito = 1L;

        Comprador comprador = Utils.crearComprador();
        comprador.setId(idComprador);

        Mockito.when(repoCompradores.findById(idComprador)).thenReturn(Optional.of(comprador));

        CarritoDeCompra carritoDeCompra = new CarritoDeCompra();
        carritoDeCompra.setComprador(comprador);

        carritoDeCompra.setId(idCarrito);

        Mockito.when(repoCarrito.findById(idCarrito)).thenReturn(Optional.of(carritoDeCompra));

        Mockito.when(repoPublicaciones.findById(idPublicacion)).thenReturn(Optional.of(publicacion));

        ResponseEntity<Object> response = this.controller.agregarItemCarrito(itemDTO,idComprador, idCarrito);

        Assertions.assertEquals(itemDTO, response.getBody());
        Assertions.assertEquals(1,carritoDeCompra.getItems().size());

        Item item = carritoDeCompra.getItems().get(0);

        Assertions.assertEquals(cantidadItem,item.getCantidad());
        Assertions.assertEquals(publicacion,item.getPublicacion());
    }

}
