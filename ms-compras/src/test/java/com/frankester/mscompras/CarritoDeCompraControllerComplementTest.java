package com.frankester.mscompras;

import com.frankester.mscompras.controllers.CarritoDeCompraControllerComplement;
import com.frankester.mscompras.exceptions.*;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.dto.CarritoDeCompraDTO;
import com.frankester.mscompras.models.dto.CarritoResponse;
import com.frankester.mscompras.models.dto.ItemDTO;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.repositories.RepoCarritoDeCompra;
import com.frankester.mscompras.repositories.RepoCompradores;
import com.frankester.mscompras.repositories.RepoPublicaciones;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class CarritoDeCompraControllerComplementTest {


    @Mock
    RepoCarritoDeCompra repoCarritoDeCompra;

    @Mock
    RepoCompradores repoCompradores;

    @Mock
    RepoPublicaciones repoPublicaciones;

    CarritoDeCompraControllerComplement controller;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.controller = new CarritoDeCompraControllerComplement(repoCarritoDeCompra,repoCompradores, repoPublicaciones);

    }


    @Test
    void sePuedeCrearUnCarrito() throws EmptyItemsException, CarritoDeCompraWithCompraException, DifferentTiendaException, PublicacionNotFoundException, CompradorNotFoundException {
        CarritoDeCompraDTO carritoDeCompraDTO = new CarritoDeCompraDTO();
        Long compradorId = 1L;
        Long publicacionId = 1L;
        carritoDeCompraDTO.setCompradorId(compradorId);


        ItemDTO item1 = new ItemDTO();
        item1.setCantidad(1);
        item1.setIdPublicacion(publicacionId);

        ItemDTO item2 = new ItemDTO();
        item2.setCantidad(2);
        item2.setIdPublicacion(publicacionId);
        carritoDeCompraDTO.setItems(List.of(item1, item2));

        Comprador comprador = Utils.crearComprador();
        Mockito.when(repoCompradores.findById(compradorId)).thenReturn(Optional.of(comprador));
        Mockito.when(repoPublicaciones.existsById(Mockito.anyLong())).thenReturn(true);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");

        Publicacion publicacion = Utils.crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Mockito.when(repoPublicaciones.findById(Mockito.anyLong())).thenReturn(Optional.of(publicacion));

        ResponseEntity<Object> response = this.controller.createCarrito(carritoDeCompraDTO);
        CarritoResponse carritoResponse = (CarritoResponse) response.getBody();
        Assertions.assertEquals(new BigDecimal("120.6"),carritoResponse.getPrecioCarrito());
        Assertions.assertEquals(compradorId,carritoResponse.getCompradorId());
        Assertions.assertEquals(2,carritoResponse.getItems().size());
    }

    @Test
    void noSePuedeCrearUnCarritoSiNoExisteAlgunaPublicacion()  {
        CarritoDeCompraDTO carritoDeCompraDTO = new CarritoDeCompraDTO();
        Long compradorId = 1L;
        Long publicacionId = 1L;
        carritoDeCompraDTO.setCompradorId(compradorId);

        ItemDTO item1 = new ItemDTO();
        item1.setCantidad(1);
        item1.setIdPublicacion(publicacionId);

        ItemDTO item2 = new ItemDTO();
        item2.setCantidad(2);
        item2.setIdPublicacion(publicacionId);
        carritoDeCompraDTO.setItems(List.of(item1, item2));

        Comprador comprador = Utils.crearComprador();
        Mockito.when(repoCompradores.findById(compradorId)).thenReturn(Optional.of(comprador));
        Mockito.when(repoPublicaciones.existsById(Mockito.anyLong())).thenReturn(true);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");

        Mockito.when(repoPublicaciones.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(PublicacionNotFoundException.class,()->this.controller.createCarrito(carritoDeCompraDTO));

    }

}
