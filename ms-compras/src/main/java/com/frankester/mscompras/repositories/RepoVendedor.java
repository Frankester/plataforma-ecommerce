package com.frankester.mscompras.repositories;

import com.frankester.mscompras.models.Tienda;
import com.frankester.mscompras.models.personas.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

@RepositoryRestResource(path = "vendedores")
public interface RepoVendedor extends JpaRepository<Vendedor, Long> {

    Optional<Vendedor> findByCuil(String cuil);

    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(Vendedor entity);
}
