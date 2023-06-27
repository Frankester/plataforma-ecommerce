package com.frankester.mscompras.repositories;

import com.frankester.mscompras.models.compra.CarritoDeCompra;
import com.frankester.mscompras.models.compra.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "compras")
public interface RepoCompra extends JpaRepository<Compra, Long> {

    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(Compra entity);
}
