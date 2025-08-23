package com.frankester.mscompras.repositories;

import com.frankester.mscompras.models.compra.CarritoDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "carritos")
public interface RepoCarritoDeCompra extends JpaRepository<CarritoDeCompra, Long> {


    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(CarritoDeCompra entity);
}
