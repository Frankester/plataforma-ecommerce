package com.frankester.mscompras.controllers;

import com.frankester.mscompras.exceptions.*;
import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.compra.*;
import com.frankester.mscompras.models.compra.mediosDePago.TarjetaDebito;
import com.frankester.mscompras.models.dto.CompraDTO;
import com.frankester.mscompras.models.dto.ConfirmarCompraDTO;
import com.frankester.mscompras.models.dto.TarjetaDebitoDTO;
import com.frankester.mscompras.models.estados.EstadoCompra;
import com.frankester.mscompras.models.personas.Comprador;
import com.frankester.mscompras.models.personas.Vendedor;
import com.frankester.mscompras.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.Optional;

@RepositoryRestController(path ="compras")
public class ComprasControllerComplement {

    private final RepoCompra repo;

    private final RepoCompradores repoCompradores;

    private final RepoVendedor repoVendedores;

    @Autowired
    public ComprasControllerComplement(RepoCompra repo, RepoCompradores repoCompradores, RepoVendedor repoVendedores){
        this.repo = repo;
        this.repoCompradores = repoCompradores;
        this.repoVendedores = repoVendedores;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Object> agregarCompra(@RequestBody CompraDTO compra)
            throws CompradorNotFoundException, CarritoDeCompraNotFoundException, MedioDePagoSinDefinirException {

        Optional<Comprador> compradorOp = repoCompradores.findById(compra.getCompradorId());

        if(compradorOp.isEmpty()){
            throw new CompradorNotFoundException();
        }

        Comprador comprador = compradorOp.get();

        if(!comprador.isActivo()){
            throw new CompradorNotFoundException();
        }

        Compra nuevaCompra = new Compra();
        //agrear metodos de pago aca
        if(compra instanceof TarjetaDebitoDTO){
            TarjetaDebito medioDePago = new TarjetaDebito();
            medioDePago.setTipoDeTarjeta(((TarjetaDebitoDTO) compra).getTipoDeTarjeta());
            medioDePago.setNumeroTarjeta(((TarjetaDebitoDTO) compra).getNumeroTarjeta());
            medioDePago.setCodigoDeSeguridad(((TarjetaDebitoDTO) compra).getCodigoDeSeguridad());
            medioDePago.setNombreTitular(((TarjetaDebitoDTO) compra).getNombreTitular());
            medioDePago.setApellidoTitular(((TarjetaDebitoDTO) compra).getApellidoTitular());

            FormaDePago formaDePago = new FormaDePago();
            formaDePago.setMedioDePago(medioDePago);
            formaDePago.setTipoMoneda(compra.getTipoMoneda());


            nuevaCompra.setFormaPago(formaDePago);
        } else {
            throw new MedioDePagoSinDefinirException();
        }


        Long carritoDeCompraId = compra.getCarritoDeCompraId();

        Optional<CarritoDeCompra> carritoDeCompraOp = comprador.getCarritos().stream()
                .filter(carrito -> carrito.getId().equals(carritoDeCompraId))
                .findFirst();

        if(carritoDeCompraOp.isEmpty()){
            throw new CarritoDeCompraNotFoundException();
        }

        CarritoDeCompra carrito = carritoDeCompraOp.get();

        nuevaCompra.setCarrito(carrito);

        carrito.setCompra(nuevaCompra);

        repo.save(nuevaCompra);

        return ResponseEntity.ok(compra);
    }

    @PostMapping(path="/{idCompra}")
    public  ResponseEntity<Object> confirmarCompras(
            @RequestBody ConfirmarCompraDTO confirmarReq,
            @PathVariable Long idCompra
    )throws VendedorNotFoundException, CompraNotFoundException, VendedorNotPermittedException{
        Optional<Compra> compraOp = this.repo.findById(idCompra);

        if(compraOp.isEmpty()){
            throw new CompraNotFoundException();
        }

        Compra compra = compraOp.get();

        if(!compra.isActivo()){
            throw new CompraNotFoundException();
        }

        Optional<Vendedor> vendedorOp = this.repoVendedores.findById(confirmarReq.getVendedorId());

        if(vendedorOp.isEmpty()){
            throw new VendedorNotFoundException();
        }

        Vendedor vendedor = vendedorOp.get();

        if(!vendedor.isActivo()){
            throw new VendedorNotFoundException();
        }

        CarritoDeCompra carrito =  compra.getCarrito();

        Tienda tiendaCompra = carrito.getItems().get(0).getPublicacion().getTienda();

        if(!tiendaCompra.getVendedor().getCuil().equals(vendedor.getCuil())){
            throw new VendedorNotPermittedException("Los items no pertenecen a este vendedor");
        }

        if(confirmarReq.isConfirmar()){
            compra.setEstadoCompra(EstadoCompra.CONFIRMADA);
        } else {
            compra.setEstadoCompra(EstadoCompra.RECHAZADA);
        }

        compra.agregarFechaCambioDeEstado(LocalDate.now());

        repo.save(compra);

        return ResponseEntity.ok(compra);
    }


    @DeleteMapping(path="/{idCompra}")
    public ResponseEntity<Object> borradoLogicoCompra(
            @PathVariable Long idCompra
    )throws CompraNotFoundException {
        Optional<Compra> compraOp = repo.findById(idCompra);

        if(compraOp.isEmpty()){
            throw new CompraNotFoundException();
        }

        Compra compra = compraOp.get();

        boolean esActivo = compra.isActivo();

        if(!esActivo){
            throw new CompraNotFoundException();
        }

        compra.setActivo(false);
        repo.save(compra);

        return ResponseEntity.ok("Compra eliminada correctamente");
    }

}
