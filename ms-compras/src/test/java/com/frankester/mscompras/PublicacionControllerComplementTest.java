package com.frankester.mscompras;

import com.frankester.mscompras.clients.ProductoPersonalizadoProxy;
import com.frankester.mscompras.controllers.CompradoresControllerComplement;
import com.frankester.mscompras.controllers.PublicacionControllerComplement;
import com.frankester.mscompras.exceptions.TiendaNotFoundException;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.dto.ProductoPersonalizadoDTO;
import com.frankester.mscompras.models.dto.PublicacionDTO;
import com.frankester.mscompras.models.estados.EstadoPublicacion;
import com.frankester.mscompras.repositories.RepoPublicaciones;
import com.frankester.mscompras.repositories.RepoTienda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

class PublicacionControllerComplementTest {

    @Mock
    RepoPublicaciones repoPublicaciones;
    @Mock
    RepoTienda repoTienda;
    @Mock
    ProductoPersonalizadoProxy proxy;

    PublicacionControllerComplement controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.controller = new PublicacionControllerComplement(repoPublicaciones,repoTienda, proxy);
    }

    @Test
    void sePuedeAgregarUnaPublicacion() throws TiendaNotFoundException {
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setNombrePublicacion("Remera de Homero Simpson");
        publicacionDTO.setNombrePersonalizacion("Dibujo de la cara de homero simpson");
        publicacionDTO.setNombreProductoBase("Remera Blanca");
        Long idTienda = 1L;
        publicacionDTO.setTiendaId(idTienda);

        Tienda tienda = new Tienda();
        tienda.setNombreTienda("Tienda todo x 2 pesos");


        Mockito.when(this.repoTienda.findById(idTienda)).thenReturn(Optional.of(tienda));

        ProductoPersonalizadoDTO productoPersonalizadoDTO = new ProductoPersonalizadoDTO();
        productoPersonalizadoDTO.setPrecio(new BigDecimal("10000.4"));

        Mockito.when(this.proxy.findByProductoPersonalizadoNombre(Mockito.anyString(),Mockito.anyString()))
                .thenReturn(ResponseEntity.ok(productoPersonalizadoDTO));
        ResponseEntity<Object> response = this.controller.agregarPublicacion(publicacionDTO);

        Assertions.assertEquals(publicacionDTO, response.getBody());
        Assertions.assertEquals(1, tienda.getPublicaciones().size());
        Publicacion publicacion = tienda.getPublicaciones().get(0);
        Assertions.assertEquals(EstadoPublicacion.VIRGENTE, publicacion.getEstado());
        Assertions.assertEquals(new BigDecimal("10000.4"), publicacion.getPrecioPublicacion());
        Assertions.assertEquals(tienda, publicacion.getTienda());
    }
}
