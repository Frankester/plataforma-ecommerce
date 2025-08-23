package com.frankester.msproductopersonalizado.controllers;

import com.frankester.msproductopersonalizado.clients.ProxyCompras;
import com.frankester.msproductopersonalizado.clients.ProxyProductoBase;
import com.frankester.msproductopersonalizado.exceptions.ProductoPersonalizadoNotFoundException;
import com.frankester.msproductopersonalizado.models.dto.ProductoBaseDTO;
import com.frankester.msproductopersonalizado.models.dto.ProductoBasePrecioDTO;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.frankester.msproductopersonalizado.models.Personalizacion;
import com.frankester.msproductopersonalizado.models.ProductoPersonalizado;
import com.frankester.msproductopersonalizado.models.dto.ProductoPersonalizadoDTO;
import com.frankester.msproductopersonalizado.repositories.RepoProductoPersonalizado;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.List;
import java.util.Optional;

@RepositoryRestController(path= "productos_personalizados")
public class ProductoPersonalizadoControllerComplement {
    private final RepoProductoPersonalizado repo;
    private final ProxyProductoBase proxy;
    private final ProxyCompras proxyCompras;

    @Autowired
    public ProductoPersonalizadoControllerComplement(RepoProductoPersonalizado repo, ProxyProductoBase proxy, ProxyCompras proxyCompras) {
        this.repo = repo;
        this.proxy = proxy;
        this.proxyCompras = proxyCompras;
    }

    @Retry(name = "default",fallbackMethod = "servicioNoDisponibleFallback")
    @PostMapping
    public ResponseEntity<Object> crearProductoPersonalizado(
            @RequestBody ProductoPersonalizadoDTO productoPersonalizadoReq ) {

        proxyCompras.existVendedorByCuil(productoPersonalizadoReq.getVendedorCuil());

        ProductoBaseDTO productoBaseReq = new ProductoBaseDTO();
        productoBaseReq.setNombre(productoPersonalizadoReq.getProductoBaseNombre());
        productoBaseReq.setPosiblePersonalizacion(productoPersonalizadoReq.getPosiblePersonalizacion());

        //verifica si el producto base y la posible personalizacion existen
        ResponseEntity<ProductoBasePrecioDTO> productoBaseRes = this.proxy.findByPosiblePersonalizacion(productoBaseReq);

        BigDecimal precioBase = productoBaseRes.getBody().getPrecio();

        Personalizacion personalizacion = new Personalizacion();
        personalizacion.setContenido(productoPersonalizadoReq.getContenido());
        personalizacion.setNombre(productoPersonalizadoReq.getNombre());
        personalizacion.setPrecio(productoPersonalizadoReq.getPrecio());
        personalizacion.setPosiblePersonalizacion(productoPersonalizadoReq.getPosiblePersonalizacion());


        ProductoPersonalizado productoPersonalizado = new ProductoPersonalizado();
        productoPersonalizado.setNombreProductoBase(productoPersonalizadoReq.getProductoBaseNombre());
        productoPersonalizado.setCuilVendedor(productoPersonalizadoReq.getVendedorCuil());
        productoPersonalizado.setPersonalizacion(personalizacion);
        productoPersonalizado.setPrecio(precioBase.add(personalizacion.getPrecio()));

        return ResponseEntity.ok(repo.save(productoPersonalizado));
    }

    @Retry(name = "default", fallbackMethod = "servicioNoDisponibleFallback")
    @GetMapping(path = "/search/findByProductoPersonalizadoNombre")
    public ResponseEntity<Object> getProductoPersonalizadoPorNombre(
            @RequestParam(name = "nombreProductoBase", required = true) String nombreProductoBase,
            @RequestParam(name = "personalizacion", required = true) String personalizacion
    ) throws ProductoPersonalizadoNotFoundException {

        //verifica si el producto base existe
       this.proxy.findByProductoBase(nombreProductoBase);

        List<ProductoPersonalizado> productoPersonalizados = this.repo.findByNombreProductoBase(nombreProductoBase);

        if(productoPersonalizados.isEmpty()){
            throw new ProductoPersonalizadoNotFoundException("No existe un producto personalizado para ese producto base");
        }

        Optional<ProductoPersonalizado> productoPersonalizadoOP = productoPersonalizados.stream()
                .filter(pp -> pp.getPersonalizacion().getNombre().equalsIgnoreCase(personalizacion)).findFirst();

        if(productoPersonalizadoOP.isEmpty()){
            throw new ProductoPersonalizadoNotFoundException("No existe un producto personalizado con esa personalizacion");
        }

        ProductoPersonalizado productoPersonalizado = productoPersonalizadoOP.get();

        if(!productoPersonalizado.isActivo()){
            throw new ProductoPersonalizadoNotFoundException();
        }

        return ResponseEntity.ok(productoPersonalizado);
    }

    @DeleteMapping(path = "/{idProductoPersonalizado}")
    public ResponseEntity<Object> borradoLogicoProductoPersonalizado(
            @PathVariable Long idProductoPersonalizado
    ) throws ProductoPersonalizadoNotFoundException {
        Optional<ProductoPersonalizado> productoPersonalizadoOP = repo.findById(idProductoPersonalizado);

        if(productoPersonalizadoOP.isEmpty()){
            throw new ProductoPersonalizadoNotFoundException();
        }

        ProductoPersonalizado productoPersonalizado = productoPersonalizadoOP.get();

        if(!productoPersonalizado.isActivo()){
            throw new ProductoPersonalizadoNotFoundException();
        }

        productoPersonalizado.setActivo(false);
        repo.save(productoPersonalizado);

        return ResponseEntity.ok("Producto personalizado borrado correctamente");
    }

    @Retry(name = "default", fallbackMethod = "servicioNoDisponibleFallback")
    @PutMapping(path = "/{idProductoPersonalizado}")
    public ResponseEntity<Object> actualizarProductoPersonalizado(
        @PathVariable Long idProductoPersonalizado,
        @RequestBody ProductoPersonalizadoDTO nuevoProductoPersonalizado
    ) throws ProductoPersonalizadoNotFoundException {

        proxyCompras.existVendedorByCuil(nuevoProductoPersonalizado.getVendedorCuil());

        ProductoBaseDTO productoBaseReq = new ProductoBaseDTO();
        productoBaseReq.setNombre(nuevoProductoPersonalizado.getProductoBaseNombre());
        productoBaseReq.setPosiblePersonalizacion(nuevoProductoPersonalizado.getPosiblePersonalizacion());

        //verifica si el producto base y la posible personalizacion existen
        ResponseEntity<ProductoBasePrecioDTO> productoBaseRes = this.proxy.findByPosiblePersonalizacion(productoBaseReq);

        BigDecimal precioBase = productoBaseRes.getBody().getPrecio();

        Optional<ProductoPersonalizado> productoPerosnalizadoGuardadoOp = this.repo.findById(idProductoPersonalizado);

        if(productoPerosnalizadoGuardadoOp.isEmpty()){
            throw new ProductoPersonalizadoNotFoundException();
        }

        ProductoPersonalizado productoPersonalizadoGuardado = productoPerosnalizadoGuardadoOp.get();

        if(!productoPersonalizadoGuardado.isActivo()){
            throw new ProductoPersonalizadoNotFoundException();
        }

        Personalizacion personalizacion = productoPersonalizadoGuardado.getPersonalizacion();
        personalizacion.setContenido(nuevoProductoPersonalizado.getContenido());
        personalizacion.setNombre(nuevoProductoPersonalizado.getNombre());
        personalizacion.setPrecio(nuevoProductoPersonalizado.getPrecio());
        personalizacion.setPosiblePersonalizacion(nuevoProductoPersonalizado.getPosiblePersonalizacion());


        productoPersonalizadoGuardado.setNombreProductoBase(nuevoProductoPersonalizado.getProductoBaseNombre());
        productoPersonalizadoGuardado.setCuilVendedor(nuevoProductoPersonalizado.getVendedorCuil());
        productoPersonalizadoGuardado.setPersonalizacion(personalizacion);
        productoPersonalizadoGuardado.setPrecio(precioBase.add(personalizacion.getPrecio()));

        return ResponseEntity.ok(repo.save(productoPersonalizadoGuardado));
    }

    private ResponseEntity<Object> servicioNoDisponibleFallback(ConnectException ex) {
        return ResponseEntity.internalServerError().body("servicio no disponible, por favor intente mas tarde");
    }


}
