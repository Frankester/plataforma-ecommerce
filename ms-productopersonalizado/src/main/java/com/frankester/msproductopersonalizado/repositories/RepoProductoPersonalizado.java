package com.frankester.msproductopersonalizado.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.frankester.msproductopersonalizado.models.ProductoPersonalizado;

import java.util.List;

@RepositoryRestResource(path="productos_personalizados")
public interface RepoProductoPersonalizado extends JpaRepository<ProductoPersonalizado, Long>{

	List<ProductoPersonalizado> findByNombreProductoBase(String nombreProductoBase);
    
    @RestResource(exported = false)
	@Override
	void delete(ProductoPersonalizado entity);

    @RestResource(exported = false)
	@Override
	void deleteById(Long id);
    
    
}