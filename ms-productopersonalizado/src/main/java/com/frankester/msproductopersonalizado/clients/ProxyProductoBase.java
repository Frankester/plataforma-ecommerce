package com.frankester.msproductopersonalizado.clients;

import com.frankester.msproductopersonalizado.models.dto.ProductoBaseDTO;
import com.frankester.msproductopersonalizado.models.dto.ProductoBasePrecioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name="productosBase", url = "http://localhost:8080/")
public interface ProxyProductoBase {

    @GetMapping(path="productos_base/search/findByNombre")
    Optional<Object> findByProductoBase(@RequestParam(required = true, name = "nombreProducto") String nombreProductoBase);

    @PostMapping("productos_base/search/findByPosiblePersonalizacion")
     ResponseEntity<ProductoBasePrecioDTO> findByPosiblePersonalizacion(@RequestBody ProductoBaseDTO posiblePersonalizacion);

}
