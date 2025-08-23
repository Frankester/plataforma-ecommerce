package com.frankester.mscompras.controllers;

import com.frankester.mscompras.exceptions.MedioDePagoAlreadyExistsException;
import com.frankester.mscompras.exceptions.VendedorNotFoundException;
import com.frankester.mscompras.models.compra.mediosDePago.MedioDePago;
import com.frankester.mscompras.models.personas.Vendedor;
import com.frankester.mscompras.repositories.RepoVendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@RepositoryRestController(path = "vendedores")
public class VendedorControllerComplement {

    @Autowired
    private RepoVendedor repo;

    @PostMapping(path = "/{idVendedor}/mediosDePago")
    public ResponseEntity<Object> agregarMediosDePago(
            @RequestBody List<MedioDePago> mediosDePago,
            @PathVariable Long idVendedor
    ) throws VendedorNotFoundException {
        Optional<Vendedor> vendedorOp = this.repo.findById(idVendedor);

        if(vendedorOp.isEmpty()){
            throw new VendedorNotFoundException();
        }

        Vendedor vendedor = vendedorOp.get();

        if(!vendedor.isActivo()){
            throw new VendedorNotFoundException();
        }

        for(MedioDePago mp : mediosDePago){
            vendedor.agregarMedioDePago(mp);
        }

        this.repo.save(vendedor);

        return ResponseEntity.ok(vendedor);
    }

    @DeleteMapping(path="/{idVendedor}")
    public ResponseEntity<Object> borradoLogicoVendedor(
            @PathVariable Long idVendedor
    )throws VendedorNotFoundException {
        Optional<Vendedor> vendedorOp = repo.findById(idVendedor);

        if(vendedorOp.isEmpty()){
            throw new VendedorNotFoundException();
        }

        Vendedor vendedor = vendedorOp.get();

        boolean esActivo = vendedor.isActivo();

        if(!esActivo){
            throw new VendedorNotFoundException();
        }

        vendedor.setActivo(false);
        repo.save(vendedor);

        return ResponseEntity.ok("Vendedor eliminado correctamente");
    }

}
