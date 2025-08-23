package com.frankester.mscompras.models.personas;

import com.frankester.mscompras.exceptions.MedioDePagoAlreadyExistsException;
import com.frankester.mscompras.models.compra.MedioDePago;
import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.Tienda;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class Vendedor extends Persona {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_vendedor", referencedColumnName = "id")
    private List<MedioDePago> mediosDePago;

    private String cuil;

    private boolean activo;

    public Vendedor(){
        this.mediosDePago = new ArrayList<>();
        this.activo = true;
    }


    public void agregarMedioDePago(MedioDePago medioDePago) throws  MedioDePagoAlreadyExistsException{

        boolean isExistMedioDePago = this.mediosDePago.stream().anyMatch(mp -> mp.getMedioDePago().equalsIgnoreCase(medioDePago.getMedioDePago()));

        if(isExistMedioDePago){
            throw new MedioDePagoAlreadyExistsException("El vendedor ya posee ese medio de pago");
        }


        this.mediosDePago.add(medioDePago);
    }


}
