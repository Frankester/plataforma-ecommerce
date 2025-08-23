package com.frankester.mscompras;

import com.frankester.mscompras.controllers.ComprasControllerComplement;
import com.frankester.mscompras.exceptions.*;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.compra.CarritoDeCompra;
import com.frankester.mscompras.models.compra.Compra;
import com.frankester.mscompras.models.compra.Item;
import com.frankester.mscompras.models.compra.mediosDePago.TarjetaDebito;
import com.frankester.mscompras.models.dto.ConfirmarCompraDTO;
import com.frankester.mscompras.models.dto.TarjetaDebitoDTO;
import com.frankester.mscompras.models.estados.EstadoCompra;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.models.personas.Vendedor;
import com.frankester.mscompras.repositories.RepoCompra;
import com.frankester.mscompras.repositories.RepoCompradores;
import com.frankester.mscompras.repositories.RepoVendedor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

class ComprasControllerComplementTests {

    ComprasControllerComplement controller;

    @Mock
    RepoCompra repoCompra;
    @Mock
    RepoCompradores repoCompradores;
    @Mock
    RepoVendedor repoVendedor;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.controller = new ComprasControllerComplement(repoCompra, repoCompradores, repoVendedor);

    }

    @Test
    void sePuedeAgregarUnaCompraTest() throws CarritoDeCompraWithCompraException, DifferentTiendaException, MedioDePagoSinDefinirException, CarritoDeCompraNotFoundException, CompradorNotFoundException {
        TarjetaDebitoDTO tarjetaDebitoDTO = new TarjetaDebitoDTO();
        Long carritoId = 1L;
        Long compradorId = 1L;

        tarjetaDebitoDTO.setCarritoDeCompraId(carritoId);
        tarjetaDebitoDTO.setNumeroTarjeta("218349821423");
        tarjetaDebitoDTO.setTipoDeTarjeta("Mastercard");
        tarjetaDebitoDTO.setNombreTitular("Franco");
        tarjetaDebitoDTO.setApellidoTitular("Callero");
        tarjetaDebitoDTO.setCodigoDeSeguridad("123");
        tarjetaDebitoDTO.setCompradorId(compradorId);
        tarjetaDebitoDTO.setTipoMoneda("ARS");

        Comprador comprador = Utils.crearComprador();

        Mockito.when(repoCompradores.findById(compradorId)).thenReturn(Optional.of(comprador));

        CarritoDeCompra carrito = new CarritoDeCompra();
        carrito.setId(carritoId);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");


        Publicacion publicacion = Utils.crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Item i = new Item();
        i.setCantidad(2);
        i.setPublicacion(publicacion);

        comprador.agregarCarrito(carrito);
        tienda.agregarPublicacion(publicacion);
        carrito.agregarItem(i);

        ResponseEntity<Object> respose = this.controller.agregarCompra(tarjetaDebitoDTO);

        Assertions.assertEquals(tarjetaDebitoDTO, respose.getBody());
        Assertions.assertNotNull(carrito.getCompra());
        Compra compra = carrito.getCompra();

        Assertions.assertEquals(EstadoCompra.PENDIENTE,compra.getEstadoCompra());
        Assertions.assertTrue(compra.getFormaPago().getMedioDePago() instanceof TarjetaDebito);

    }

    @Test
    void noSePuedeComprarSiNoExisteElComprador() {
        TarjetaDebitoDTO tarjetaDebitoDTO = new TarjetaDebitoDTO();
        Long carritoId = 1L;
        Long compradorId = 1L;

        tarjetaDebitoDTO.setCarritoDeCompraId(carritoId);
        tarjetaDebitoDTO.setNumeroTarjeta("218349821423");
        tarjetaDebitoDTO.setTipoDeTarjeta("Mastercard");
        tarjetaDebitoDTO.setNombreTitular("Franco");
        tarjetaDebitoDTO.setApellidoTitular("Callero");
        tarjetaDebitoDTO.setCodigoDeSeguridad("123");
        tarjetaDebitoDTO.setCompradorId(compradorId);
        tarjetaDebitoDTO.setTipoMoneda("ARS");

        Mockito.when(repoCompradores.findById(compradorId)).thenReturn(Optional.empty());
        Assertions.assertThrows(CompradorNotFoundException.class, () -> this.controller.agregarCompra(tarjetaDebitoDTO));
    }

    @Test
    void noSePuedeComprarElCompradorFueEliminado() {
        TarjetaDebitoDTO tarjetaDebitoDTO = new TarjetaDebitoDTO();
        Long carritoId = 1L;
        Long compradorId = 1L;

        tarjetaDebitoDTO.setCarritoDeCompraId(carritoId);
        tarjetaDebitoDTO.setNumeroTarjeta("218349821423");
        tarjetaDebitoDTO.setTipoDeTarjeta("Mastercard");
        tarjetaDebitoDTO.setNombreTitular("Franco");
        tarjetaDebitoDTO.setApellidoTitular("Callero");
        tarjetaDebitoDTO.setCodigoDeSeguridad("123");
        tarjetaDebitoDTO.setCompradorId(compradorId);
        tarjetaDebitoDTO.setTipoMoneda("ARS");

        Comprador comprador = Utils.crearComprador();
        comprador.setId(compradorId);
        comprador.setActivo(false);

        Mockito.when(repoCompradores.findById(compradorId)).thenReturn(Optional.of(comprador));

        Assertions.assertThrows(CompradorNotFoundException.class, () -> this.controller.agregarCompra(tarjetaDebitoDTO));
    }

    @Test
    void noSePuedeComprarUnCarritoQueNoSeaDelComprador() {
        TarjetaDebitoDTO tarjetaDebitoDTO = new TarjetaDebitoDTO();
        Long carritoId = 1L;
        Long compradorId = 1L;

        tarjetaDebitoDTO.setCarritoDeCompraId(carritoId);
        tarjetaDebitoDTO.setNumeroTarjeta("218349821423");
        tarjetaDebitoDTO.setTipoDeTarjeta("Mastercard");
        tarjetaDebitoDTO.setNombreTitular("Franco");
        tarjetaDebitoDTO.setApellidoTitular("Callero");
        tarjetaDebitoDTO.setCodigoDeSeguridad("123");
        tarjetaDebitoDTO.setCompradorId(compradorId);
        tarjetaDebitoDTO.setTipoMoneda("ARS");

        Comprador comprador = Utils.crearComprador();
        comprador.setId(compradorId);

        Mockito.when(repoCompradores.findById(compradorId)).thenReturn(Optional.of(comprador));

        Assertions.assertThrows(CarritoDeCompraNotFoundException.class, ()-> this.controller.agregarCompra(tarjetaDebitoDTO));

    }

    @Test
    void sePuedeConfirmarCompras() throws CarritoDeCompraWithCompraException, DifferentTiendaException, CompraNotFoundException, VendedorNotPermittedException, VendedorNotFoundException {
        ConfirmarCompraDTO confirmarCompraDTO = new ConfirmarCompraDTO();
        confirmarCompraDTO.setConfirmar(true);
        Long vendedorId = 2L;
        Long compraId = 1L;
        Long carritoId = 1L;
        confirmarCompraDTO.setVendedorId(vendedorId);

        Vendedor vendedor = new Vendedor();
        vendedor.setNombre("Franco");
        vendedor.setApellido("Callero");
        vendedor.setCuil("20-34243233-3");
        vendedor.setFechaNacimiento(LocalDate.of(2001,6,10));

        Mockito.when(repoVendedor.findById(vendedorId)).thenReturn(Optional.of(vendedor));

        Compra compra = new Compra();
        compra.setId(compraId);

        CarritoDeCompra carrito = new CarritoDeCompra();
        carrito.setId(carritoId);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");


        Publicacion publicacion = Utils.crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Item i = new Item();
        i.setCantidad(2);
        i.setPublicacion(publicacion);

        tienda.agregarPublicacion(publicacion);
        carrito.agregarItem(i);

        tienda.setVendedor(vendedor);
        compra.setCarrito(carrito);

        Mockito.when(repoCompra.findById(compraId)).thenReturn(Optional.of(compra));

        ResponseEntity<Object> response = this.controller.confirmarCompras(confirmarCompraDTO,compraId);
        Assertions.assertEquals(compra, response.getBody());

        Assertions.assertEquals(1, compra.getFechasCambioDeEstado().size());
        Assertions.assertEquals(EstadoCompra.CONFIRMADA, compra.getEstadoCompra());

    }


    @Test
    void noSePuedeConfirmarComprasDeOtroVendedor() throws CarritoDeCompraWithCompraException, DifferentTiendaException {
        ConfirmarCompraDTO confirmarCompraDTO = new ConfirmarCompraDTO();
        confirmarCompraDTO.setConfirmar(true);
        Long vendedorId = 2L;
        Long compraId = 1L;
        Long carritoId = 1L;
        confirmarCompraDTO.setVendedorId(vendedorId);

        Vendedor vendedor = new Vendedor();
        vendedor.setNombre("Franco");
        vendedor.setApellido("Callero");
        vendedor.setCuil("20-34243233-3");
        vendedor.setFechaNacimiento(LocalDate.of(2001,6,10));

        Vendedor vendedor2 = new Vendedor();
        vendedor2.setNombre("Francisco");
        vendedor2.setApellido("Caballero");
        vendedor2.setCuil("20-34244133-3");
        vendedor2.setFechaNacimiento(LocalDate.of(2001,6,10));

        Mockito.when(repoVendedor.findById(vendedorId)).thenReturn(Optional.of(vendedor));

        Compra compra = new Compra();
        compra.setId(compraId);

        CarritoDeCompra carrito = new CarritoDeCompra();
        carrito.setId(carritoId);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");


        Publicacion publicacion = Utils.crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Item i = new Item();
        i.setCantidad(2);
        i.setPublicacion(publicacion);

        tienda.agregarPublicacion(publicacion);
        carrito.agregarItem(i);

        tienda.setVendedor(vendedor2);//tienda de otro vendedor
        compra.setCarrito(carrito);

        Mockito.when(repoCompra.findById(compraId)).thenReturn(Optional.of(compra));

        Assertions.assertThrows(VendedorNotPermittedException.class, ()->this.controller.confirmarCompras(confirmarCompraDTO,compraId));

    }

}
