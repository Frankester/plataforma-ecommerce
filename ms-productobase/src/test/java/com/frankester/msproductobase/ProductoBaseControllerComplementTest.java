package com.frankester.msproductobase;

import com.frankester.msproductobase.controllers.ProductoBaseControllerComplement;
import com.frankester.msproductobase.exceptions.PosiblePersonalizacionExistenteException;
import com.frankester.msproductobase.exceptions.PosiblePersonalizacionNotFoundException;
import com.frankester.msproductobase.exceptions.ProductoBaseNotFoundException;
import com.frankester.msproductobase.models.PosiblePersonalizacion;
import com.frankester.msproductobase.models.ProductoBase;
import com.frankester.msproductobase.models.dto.PosiblePersonalizacionDTO;
import com.frankester.msproductobase.models.dto.ProductoBaseDTO;
import com.frankester.msproductobase.repositories.RepoProductoBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;

class ProductoBaseControllerComplementTest {

    @Mock
    RepoProductoBase repoProductoBase;

    ProductoBaseControllerComplement controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.controller = new ProductoBaseControllerComplement(repoProductoBase);
    }

    @Test
    void sePuedeAgregarUnaPersonalizacion() throws ProductoBaseNotFoundException, PosiblePersonalizacionExistenteException {
        Long idProductoBase = 1L;

        PosiblePersonalizacionDTO posiblePersonalizacionDTO = new PosiblePersonalizacionDTO();
        posiblePersonalizacionDTO.setAreaPersonalizacion("Frontal centro");
        posiblePersonalizacionDTO.setTipoPersonalizacion("Dibujo");

        ProductoBase productoBase = new ProductoBase();
        productoBase.setNombre("Remera Blanca");
        productoBase.setPrecio(new BigDecimal("12000.5"));
        productoBase.setDescripcion("Remera Blanca lista talle L");
        productoBase.setTiempoDeFabricacion(LocalTime.of(2,30));

        Mockito.when(repoProductoBase.findById(idProductoBase)).thenReturn(Optional.of(productoBase));
        Mockito.when(repoProductoBase.save(Mockito.any())).thenReturn(productoBase);


        ResponseEntity<Object> response = this.controller.agregarPosiblePersonalizacion(idProductoBase,posiblePersonalizacionDTO);

        Assertions.assertEquals(productoBase, response.getBody());
        Assertions.assertEquals(1, productoBase.getPosiblesPersonalizaciones().size());

        PosiblePersonalizacion posiblePersonalizacion = productoBase.getPosiblesPersonalizaciones().get(0);

        Assertions.assertEquals(posiblePersonalizacionDTO.getAreaPersonalizacion(), posiblePersonalizacion.getAreaPersonalizacion());
        Assertions.assertEquals(posiblePersonalizacionDTO.getTipoPersonalizacion(), posiblePersonalizacion.getTipoPersonalizacion());
    }

    @Test
    void sePuedeObtenerUnProductoBasePorNombreYPosiblePersonalizacion() throws ProductoBaseNotFoundException, PosiblePersonalizacionExistenteException, PosiblePersonalizacionNotFoundException {

        ProductoBaseDTO productoBaseDTO = new ProductoBaseDTO();
        productoBaseDTO.setNombre("Remera Blanca");
        PosiblePersonalizacionDTO posiblePersonalizacionDTO = new PosiblePersonalizacionDTO();
        posiblePersonalizacionDTO.setAreaPersonalizacion("Frontal centro");
        posiblePersonalizacionDTO.setTipoPersonalizacion("Dibujo");
        productoBaseDTO.setPosiblePersonalizacion(posiblePersonalizacionDTO);

        ProductoBase productoBase = new ProductoBase();
        productoBase.setNombre("Remera Blanca");
        productoBase.setPrecio(new BigDecimal("12000.5"));
        productoBase.setDescripcion("Remera Blanca lista talle L");
        productoBase.setTiempoDeFabricacion(LocalTime.of(2,30));

        Mockito.when(this.repoProductoBase.findByNombre(Mockito.anyString())).thenReturn(Optional.of(productoBase));

        PosiblePersonalizacion posiblePersonalizacion = new PosiblePersonalizacion();
        posiblePersonalizacion.setTipoPersonalizacion(posiblePersonalizacionDTO.getTipoPersonalizacion());
        posiblePersonalizacion.setAreaPersonalizacion(posiblePersonalizacionDTO.getAreaPersonalizacion());

        productoBase.agregarPosiblesPersonalizacion(posiblePersonalizacion);

        ResponseEntity<Object> response = this.controller.obtenerProductoBase(productoBaseDTO);
        Assertions.assertEquals(productoBase, response.getBody());
    }


}
