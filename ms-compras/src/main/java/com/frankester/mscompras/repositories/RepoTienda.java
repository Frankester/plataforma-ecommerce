package com.frankester.mscompras.repositories;

import com.frankester.mscompras.models.Publicacion;
import com.frankester.mscompras.models.Tienda;
import com.sun.jdi.LongValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "tiendas")
public interface RepoTienda extends JpaRepository<Tienda, Long> {

    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(Tienda entity);
}
