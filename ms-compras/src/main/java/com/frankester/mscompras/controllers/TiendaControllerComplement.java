package com.frankester.mscompras.controllers;

import com.frankester.mscompras.exceptions.TiendaNotFoundException;
import com.frankester.mscompras.exceptions.VendedorNotFoundException;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.dto.TiendaDTO;
import com.frankester.mscompras.models.personas.Vendedor;
import com.frankester.mscompras.repositories.RepoTienda;
import com.frankester.mscompras.repositories.RepoVendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@RepositoryRestController(path = "tiendas")
public class TiendaControllerComplement {

    @Autowired
    RepoTienda repo;

    @Autowired
    RepoVendedor repoVendedores;

    @PostMapping
    public ResponseEntity<Object> crearNuevaTienda(@RequestBody TiendaDTO tienda)
    throws VendedorNotFoundException{

        Optional<Vendedor> vendedorOp = repoVendedores.findById(tienda.getVendedorId());

        if(vendedorOp.isEmpty()){
            throw new VendedorNotFoundException();
        }

        Vendedor vendedor = vendedorOp.get();

        if(!vendedor.isActivo()){
            throw new VendedorNotFoundException();
        }

        Tienda nuevaTienda = new Tienda();
        nuevaTienda.setNombreTienda(tienda.getNombre());
        nuevaTienda.setVendedor(vendedor);

        return ResponseEntity.ok(repo.save(nuevaTienda));
    }

    @DeleteMapping(path="/{idTienda}")
    public ResponseEntity<Object> borradoLogicoTienda(
            @PathVariable Long idTienda
    )throws TiendaNotFoundException {
        Optional<Tienda> tiendaOp = repo.findById(idTienda);

        if(tiendaOp.isEmpty()){
            throw new TiendaNotFoundException();
        }

        Tienda tienda = tiendaOp.get();

        boolean esActivo = tienda.isActivo();

        if(!esActivo){
            throw new TiendaNotFoundException();
        }

        tienda.setActivo(false);
        repo.save(tienda);

        return ResponseEntity.ok("Tienda eliminada correctamente");
    }
}
