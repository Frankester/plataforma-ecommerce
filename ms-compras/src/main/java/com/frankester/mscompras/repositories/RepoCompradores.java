package com.frankester.mscompras.repositories;

import com.frankester.mscompras.models.compra.Compra;
import com.frankester.mscompras.models.personas.Comprador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path = "compradores")
public interface RepoCompradores extends JpaRepository<Comprador, Long> {
    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(Comprador entity);
}
