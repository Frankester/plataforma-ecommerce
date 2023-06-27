package com.frankester.msproductobase.repositories;

import com.frankester.msproductobase.models.ProductoBase;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(path="productos_base")
public interface RepoProductoBase extends JpaRepository<ProductoBase, Long> {

    Optional<ProductoBase> findByNombre(String nombreProducto);


    @RestResource(exported = false)
    @Override
    void deleteById(Long aLong);

    @RestResource(exported = false)
    @Override
    void delete(ProductoBase entity);
}
