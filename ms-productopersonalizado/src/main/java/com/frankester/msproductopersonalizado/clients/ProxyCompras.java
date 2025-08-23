package com.frankester.msproductopersonalizado.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name ="compras", url="http://localhost:8082/")
public interface ProxyCompras {

    @GetMapping(path="vendedores/search/findByCuil")
    Optional<Object> existVendedorByCuil(@PathVariable String cuil);



}
