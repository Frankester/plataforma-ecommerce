package com.frankester.msproductopersonalizado.clients;

import com.frankester.msproductopersonalizado.models.dto.ProductoBaseDTO;
import com.frankester.msproductopersonalizado.models.dto.ProductoBaseDTO2;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name ="compras", url="http://localhost:8082/")
public interface ProxyCompras {

    @GetMapping(path="vendedores/search/findByCuil")
    Optional<Object> existVendedorByCuil(@PathVariable String cuil);



}
