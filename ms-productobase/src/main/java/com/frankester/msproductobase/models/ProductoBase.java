package com.frankester.msproductobase.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.frankester.msproductobase.exceptions.PosiblePersonalizacionExistenteException;
import com.frankester.msproductobase.exceptions.ProductoBaseNotFoundException;

@Data
@Entity
public class ProductoBase {
    @Id
    @GeneratedValue
    private Long id;

    private String nombre;
    private String descripcion;
    private Integer precio;

    @Column(name="tiempo_estimado_fabricacion", columnDefinition = "TIME")
    private LocalTime tiempoDeFabricacion;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="id_producto_base", referencedColumnName = "id")
    private List<PosiblePersonalizacion> posiblesPersonalizaciones;

    private boolean esActivo;

    public ProductoBase(){
        this.posiblesPersonalizaciones = new ArrayList<>();
        this.esActivo = true;
    }

    public void agregarPosiblesPersonalizacion(PosiblePersonalizacion nuevaPosiblePersonalizacion) throws PosiblePersonalizacionExistenteException, ProductoBaseNotFoundException{
       boolean yaExistePosiblePersonalizacion = this.posiblesPersonalizaciones.stream()
            .anyMatch(pp -> (pp.getAreaPersonalizacion().equalsIgnoreCase(nuevaPosiblePersonalizacion.getAreaPersonalizacion()) 
            && pp.getTipoPersonalizacion().equalsIgnoreCase(nuevaPosiblePersonalizacion.getTipoPersonalizacion()))
        );
       
   
       if(yaExistePosiblePersonalizacion){
            throw new PosiblePersonalizacionExistenteException("El producto base ya tiene agregada esa posible personalizacion");
       }
    
       if(!this.esActivo){
        throw new ProductoBaseNotFoundException("El producto base no existe");
       }


        this.posiblesPersonalizaciones.add(nuevaPosiblePersonalizacion);
    }

}
