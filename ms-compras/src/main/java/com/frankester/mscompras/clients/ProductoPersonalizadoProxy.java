package com.frankester.mscompras.clients;

import com.frankester.mscompras.models.dto.ProductoPersonalizadoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="productoPersonalizado", url = "http://localhost:8081/")
public interface ProductoPersonalizadoProxy {


    @GetMapping(path="productos_personalizados/search/findByProductoPersonalizadoNombre")
    ResponseEntity<ProductoPersonalizadoDTO> findByProductoPersonalizadoNombre(@RequestParam String nombreProductoBase, @RequestParam String personalizacion);

}
