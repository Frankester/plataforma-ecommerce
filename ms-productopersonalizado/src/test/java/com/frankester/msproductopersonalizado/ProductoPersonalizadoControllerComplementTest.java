package com.frankester.msproductopersonalizado;

import com.frankester.msproductopersonalizado.clients.ProxyCompras;
import com.frankester.msproductopersonalizado.clients.ProxyProductoBase;
import com.frankester.msproductopersonalizado.controllers.ProductoPersonalizadoControllerComplement;
import com.frankester.msproductopersonalizado.exceptions.ProductoPersonalizadoNotFoundException;
import com.frankester.msproductopersonalizado.models.Personalizacion;
import com.frankester.msproductopersonalizado.models.PosiblePersonalizacion;
import com.frankester.msproductopersonalizado.models.ProductoPersonalizado;
import com.frankester.msproductopersonalizado.repositories.RepoProductoPersonalizado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

class ProductoPersonalizadoControllerComplementTest {

    @Mock
    RepoProductoPersonalizado repoProductoPersonalizado;
    @Mock
    ProxyProductoBase proxyProductoBase;
    @Mock
    ProxyCompras proxyCompras;

    ProductoPersonalizadoControllerComplement controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.controller = new ProductoPersonalizadoControllerComplement(repoProductoPersonalizado, proxyProductoBase, proxyCompras);
    }

    @Test
    void sePuedeObtenerUnProductoPersonalizadoPorNombre() throws ProductoPersonalizadoNotFoundException {
        String nombreProductoBase = "Remera blanca";
        String nombrePersonalizacion = "Dibujo de Homero simpson";

        ProductoPersonalizado productoPersonalizado = new ProductoPersonalizado();
        productoPersonalizado.setNombreProductoBase(nombreProductoBase);
        productoPersonalizado.setPrecio(new BigDecimal("100000.5"));
        productoPersonalizado.setCuilVendedor("20-23423289-3");

        Personalizacion personalizacion = new Personalizacion();
        personalizacion.setNombre(nombrePersonalizacion);
        personalizacion.setPrecio(new BigDecimal("20000.3"));
        personalizacion.setContenido("Dibujo frontal de Hombero simpson comiendo una rosquilla");

        PosiblePersonalizacion posiblePersonalizacion = new PosiblePersonalizacion();
        posiblePersonalizacion.setAreaPersonalizacion("Frontral centro");
        posiblePersonalizacion.setTipoPersonalizacion("Dibujo");

        personalizacion.setPosiblePersonalizacion(posiblePersonalizacion);
        productoPersonalizado.setPersonalizacion(personalizacion);

        Mockito.when(this.repoProductoPersonalizado.findByNombreProductoBase(nombreProductoBase))
                .thenReturn(List.of(productoPersonalizado));

        ResponseEntity<Object> response = this.controller.getProductoPersonalizadoPorNombre(nombreProductoBase, nombrePersonalizacion);

        Assertions.assertEquals(productoPersonalizado, response.getBody());
    }


}
