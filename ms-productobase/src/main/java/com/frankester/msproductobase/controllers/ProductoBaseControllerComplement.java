package com.frankester.msproductobase.controllers;

import com.frankester.msproductobase.exceptions.PosiblePersonalizacionExistenteException;
import com.frankester.msproductobase.exceptions.PosiblePersonalizacionNotFoundException;
import com.frankester.msproductobase.exceptions.ProductoBaseNotFoundException;
import com.frankester.msproductobase.models.PosiblePersonalizacion;
import com.frankester.msproductobase.models.ProductoBase;
import com.frankester.msproductobase.models.dto.PosiblePersonalizacionDTO;
import com.frankester.msproductobase.models.dto.ProductoBaseDTO;
import com.frankester.msproductobase.repositories.RepoProductoBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RepositoryRestController
public class ProductoBaseControllerComplement {


    private final RepoProductoBase repo;

    @Autowired
    public ProductoBaseControllerComplement(RepoProductoBase repo) {
        this.repo = repo;
    }

    @DeleteMapping("productos_base/{idProductoBase}")
    public ResponseEntity<Object> borradoLogico(@PathVariable Long idProductoBase) throws ProductoBaseNotFoundException{
        Optional<ProductoBase> productoBaseOp = repo.findById(idProductoBase);

        if(productoBaseOp.isEmpty()){
            throw new ProductoBaseNotFoundException("el producto base solicitado no existe", idProductoBase);
        }

        ProductoBase productoBase = productoBaseOp.get();

        if(!productoBase.isEsActivo()){
            throw new ProductoBaseNotFoundException("el producto base solicitado no existe");
        }
        productoBase.setEsActivo(false);

        repo.save(productoBase);

        return ResponseEntity.ok("Producto Base eliminado correctamente");
    }

    @PostMapping("productos_base/{idProductoBase}/posiblePersonalizaciones")
    public ResponseEntity<Object> agregarPosiblePersonalizacion(
            @PathVariable Long idProductoBase,
            @RequestBody PosiblePersonalizacionDTO posiblePersonalizacionDTO
    ) throws ProductoBaseNotFoundException, PosiblePersonalizacionExistenteException {
        Optional<ProductoBase> productoBaseOp = repo.findById(idProductoBase);

        if(productoBaseOp.isEmpty()){
            throw new ProductoBaseNotFoundException("el producto base solicitado no existe", idProductoBase);
        }

        ProductoBase productoBase = productoBaseOp.get();

        PosiblePersonalizacion posiblePersonalizacion = new PosiblePersonalizacion();
        posiblePersonalizacion.setAreaPersonalizacion(posiblePersonalizacionDTO.getAreaPersonalizacion());
        posiblePersonalizacion.setTipoPersonalizacion(posiblePersonalizacionDTO.getTipoPersonalizacion());

        productoBase.agregarPosiblesPersonalizacion(posiblePersonalizacion);

        return ResponseEntity.ok(repo.save(productoBase));
    }

    @PostMapping("productos_base/search/findByPosiblePersonalizacion")
    public ResponseEntity<Object> obtenerProductoBase(
            @RequestBody ProductoBaseDTO  posiblePersonalizacion
    ) throws ProductoBaseNotFoundException, PosiblePersonalizacionNotFoundException{

        String productoBaseNombre = posiblePersonalizacion.getNombre();
        Optional<ProductoBase> productoBaseOP = repo.findByNombre(productoBaseNombre);

        if(productoBaseOP.isEmpty()){
            throw new ProductoBaseNotFoundException("No se encontro un producto base con ese nombre");
        }

        ProductoBase productoBase = productoBaseOP.get();

        List<PosiblePersonalizacion> posiblesPersonalizaciones = productoBase.getPosiblesPersonalizaciones(); 

        PosiblePersonalizacionDTO posiblePersonalizacionReq = posiblePersonalizacion.getPosiblePersonalizacion();

        boolean existePosiblePersonalizacion = posiblesPersonalizaciones.stream()
            .anyMatch(pp -> (pp.getAreaPersonalizacion().equalsIgnoreCase(posiblePersonalizacionReq.getAreaPersonalizacion()) 
            && pp.getTipoPersonalizacion().equalsIgnoreCase(posiblePersonalizacionReq.getTipoPersonalizacion()))
        );

        if(!existePosiblePersonalizacion){
            throw new PosiblePersonalizacionNotFoundException("No existe la posible personalizacion para el producto base solicitado");
        }


        return ResponseEntity.ok(productoBase);
    }

}
