package com.frankester.mscompras.controllers;

import com.frankester.mscompras.clients.ProductoPersonalizadoProxy;
import com.frankester.mscompras.exceptions.CompradorNotFoundException;
import com.frankester.mscompras.exceptions.PublicacionNotFoundException;
import com.frankester.mscompras.exceptions.TiendaNotFoundException;
import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.dto.ProductoPersonalizadoDTO;
import com.frankester.mscompras.models.dto.PublicacionDTO;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.repositories.RepoPublicaciones;
import com.frankester.mscompras.repositories.RepoTienda;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.ConnectException;
import java.time.LocalDate;
import java.util.Optional;

@RepositoryRestController(path = "publicaciones")
public class PublicacionControllerComplement {

    @Autowired
    RepoPublicaciones repo;

    @Autowired
    RepoTienda repoTienda;

    @Autowired
    ProductoPersonalizadoProxy proxy;

    @Retry(name = "default", fallbackMethod = "serviceDisable")
    @PostMapping
    public ResponseEntity<Object> agregarPublicacion(
            @RequestBody PublicacionDTO nuevaPublicacion
    )throws TiendaNotFoundException{

        Optional<Tienda> tiendaOp = this.repoTienda.findById(nuevaPublicacion.getTiendaId());

        if(tiendaOp.isEmpty()){
            throw new TiendaNotFoundException();
        }

        Tienda tienda = tiendaOp.get();

        if(!tienda.isActivo()){
            throw new TiendaNotFoundException();
        }

        ResponseEntity<ProductoPersonalizadoDTO> prodPersonalizadoRes = proxy.findByProductoPersonalizadoNombre(nuevaPublicacion.getNombreProductoBase(), nuevaPublicacion.getNombrePersonalizacion());

        Integer precioPublicacion = prodPersonalizadoRes.getBody().getPrecio();

        Publicacion publicacion = new Publicacion();
        publicacion.setNombrePublicacion(nuevaPublicacion.getNombrePublicacion());
        publicacion.setNombrePersonalizacion(nuevaPublicacion.getNombrePersonalizacion());
        publicacion.setNombreProductoBase(nuevaPublicacion.getNombreProductoBase());
        publicacion.setFechaDePublicacion(LocalDate.now());
        publicacion.setTienda(tienda);
        publicacion.setPrecioPublicacion(precioPublicacion);

        tienda.agregarPublicacion(publicacion);

        repo.save(publicacion);

        return ResponseEntity.ok(nuevaPublicacion);
    }


    @DeleteMapping(path="/{idPublicacion}")
    public ResponseEntity<Object> borradoLogicoPublicacion(
            @PathVariable Long idPublicacion
    )throws PublicacionNotFoundException {
        Optional<Publicacion> publicacionOp = repo.findById(idPublicacion);

        if(publicacionOp.isEmpty()){
            throw new PublicacionNotFoundException();
        }

        Publicacion publicacion = publicacionOp.get();

        boolean esActivo = publicacion.isActivo();

        if(!esActivo){
            throw new PublicacionNotFoundException();
        }

        publicacion.setActivo(false);
        repo.save(publicacion);

        return ResponseEntity.ok("Publicacion eliminada correctamente");
    }

    private ResponseEntity<Object> serviceDisable(ConnectException ex){
        return ResponseEntity.internalServerError().body("El servicio no se encuentra disponible de momento, intente de nuevo mas tarde");
    }
}
