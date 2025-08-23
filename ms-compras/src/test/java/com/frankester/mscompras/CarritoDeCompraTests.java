package com.frankester.mscompras;

import com.frankester.mscompras.exceptions.CarritoDeCompraWithCompraException;
import com.frankester.mscompras.exceptions.DifferentTiendaException;
import com.frankester.mscompras.exceptions.EmptyItemsException;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.compra.*;
import com.frankester.mscompras.models.estados.EstadoCompra;
import com.frankester.mscompras.models.estados.EstadoPublicacion;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.models.personas.contacto.MedioDeContacto;
import com.frankester.mscompras.models.personas.documentos.Documento;
import com.frankester.mscompras.models.personas.documentos.TipoDeDocumento;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;

class CarritoDeCompraTests {

    @Mock
    MedioDeContacto medioDeContactoMock;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    Comprador crearComprador(){
        Comprador comprador = new Comprador();
        comprador.setNombre("Franco");
        comprador.setApellido("Callero");
        comprador.setFechaNacimiento(LocalDate.of(2001,6,10));
        Documento documento = new Documento(TipoDeDocumento.DNI, "43244599");
        comprador.setDocumento(documento);
        comprador.agregarMedioDeContacto(medioDeContactoMock);
        return comprador;
    }

    Publicacion crearPublicacion(String nombrePublicacion, String precio, String nombreProductoBase, String nombrePersonalizacion, Tienda tienda){
        Publicacion publicacion = new Publicacion();
        publicacion.setNombrePublicacion(nombrePublicacion);
        publicacion.setFechaDePublicacion(LocalDate.of(2025, 8,10));
        publicacion.setPrecioPublicacion(new BigDecimal(precio));
        publicacion.setEstado(EstadoPublicacion.VIRGENTE);
        publicacion.setNombreProductoBase(nombreProductoBase);
        publicacion.setNombrePersonalizacion(nombrePersonalizacion);



        publicacion.setTienda(tienda);
        return publicacion;
    }


    @Test
    void sePuedeObtenerElPrecioTotalDelCarrito() throws CarritoDeCompraWithCompraException, DifferentTiendaException, EmptyItemsException {

        Comprador comprador = crearComprador();
        CarritoDeCompra carrito = new CarritoDeCompra();

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");


        Publicacion publicacion = crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Item i = new Item();
        i.setCantidad(2);
        i.setPublicacion(publicacion);

        Publicacion publicacion2 =crearPublicacion(
                "Zapatilla artística talle L",
                "200000.2",
                "Zapatilla blanca talle L",
                "Dibujo artístico",
                tienda);

        Item i2 = new Item();
        i2.setCantidad(4);
        i2.setPublicacion(publicacion2);

        comprador.agregarCarrito(carrito);
        tienda.agregarPublicacion(publicacion);
        tienda.agregarPublicacion(publicacion2);
        carrito.agregarItem(i);
        carrito.agregarItem(i2);

        Assertions.assertEquals(new BigDecimal("800081.2"), carrito.calcularPrecioTotal());
    }

    @Test
    void seActualizaElPrecioTotalDelCarritoCorrectamente() throws CarritoDeCompraWithCompraException, DifferentTiendaException, EmptyItemsException {

        Comprador comprador = crearComprador();
        CarritoDeCompra carrito = new CarritoDeCompra();

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");


        Publicacion publicacion = crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Item i = new Item();
        i.setCantidad(2);
        i.setPublicacion(publicacion);

        Publicacion publicacion2 =crearPublicacion(
                "Zapatilla artística talle L",
                "200000.2",
                "Zapatilla blanca talle L",
                "Dibujo artístico",
                tienda);

        Item i2 = new Item();
        i2.setCantidad(4);
        i2.setPublicacion(publicacion2);

        comprador.agregarCarrito(carrito);
        tienda.agregarPublicacion(publicacion);
        tienda.agregarPublicacion(publicacion2);
        carrito.agregarItem(i);
        carrito.agregarItem(i2);

        Assertions.assertEquals(new BigDecimal("800081.2"), carrito.calcularPrecioTotal());

        i.setCantidad(3);
        publicacion.setPrecioPublicacion(new BigDecimal("40000.6"));

        Assertions.assertEquals(new BigDecimal("920002.6"), carrito.calcularPrecioTotal());
    }

    @Test
    void noSePuedeCalcularElPrecioTotalDeUnCarritoVacio()  {

        Comprador comprador = crearComprador();
        CarritoDeCompra carrito = new CarritoDeCompra();


        comprador.agregarCarrito(carrito);

        Assertions.assertThrows(EmptyItemsException.class, carrito::calcularPrecioTotal);
    }

    @Test
    void noSePuedeAgregarItemsDeDiferentesTiendas() throws CarritoDeCompraWithCompraException, DifferentTiendaException {
        Comprador comprador = crearComprador();
        CarritoDeCompra carrito = new CarritoDeCompra();

        comprador.agregarCarrito(carrito);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");

        Tienda tienda2 = new Tienda();
        tienda2.setNombreTienda("El Zapas");

        Publicacion publicacion = crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Item i = new Item();
        i.setCantidad(2);
        i.setPublicacion(publicacion);

        Publicacion publicacion2 =crearPublicacion(
                "Zapatilla artística talle L",
                "200000.2",
                "Zapatilla blanca talle L",
                "Dibujo artístico",
                tienda2); //diferente tienda

        Item i2 = new Item();
        i2.setCantidad(4);
        i2.setPublicacion(publicacion2);

        carrito.agregarItem(i);

        Assertions.assertThrows(DifferentTiendaException.class, ()->{
            carrito.agregarItem(i2);
        });
    }

    @Test
    void noSePuedeModificarElCarritoComprado() throws CarritoDeCompraWithCompraException, DifferentTiendaException {
        Comprador comprador = crearComprador();
        CarritoDeCompra carrito = new CarritoDeCompra();

        comprador.agregarCarrito(carrito);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");

        Tienda tienda2 = new Tienda();
        tienda2.setNombreTienda("El Zapas");

        Publicacion publicacion = crearPublicacion(
                "Remera de Homero Simpson",
                "40.2",
                "Remera Blanca",
                "Dibujo de la cara de homero simpson",
                tienda);

        Item i = new Item();
        i.setCantidad(2);
        i.setPublicacion(publicacion);

        Publicacion publicacion2 =crearPublicacion(
                "Zapatilla artística talle L",
                "200000.2",
                "Zapatilla blanca talle L",
                "Dibujo artístico",
                tienda2); //diferente tienda

        Item i2 = new Item();
        i2.setCantidad(4);
        i2.setPublicacion(publicacion2);

        carrito.agregarItem(i);

        Compra compra = new Compra();
        compra.setCarrito(carrito);
        compra.setEstadoCompra(EstadoCompra.PENDIENTE);
        compra.setFechaInicio(LocalDate.of(2025,10,12));
        FormaDePago formaDePago = new FormaDePago();
        formaDePago.setNumeroTarjeta("12345783242");
        formaDePago.setTipoMoneda("AR$");
        formaDePago.setCodigoDeSeguridad("123");
        MedioDePago medioDePago = new MedioDePago();
        medioDePago.setMedioDePago("Tarjeta de débito MasterCard");
        formaDePago.setMedioDePago(medioDePago);
        compra.setFormaPago(formaDePago);

        carrito.setCompra(compra);

        Assertions.assertThrows(CarritoDeCompraWithCompraException.class, ()->
                carrito.agregarItem(i2)
        );
    }

}
