package com.frankester.mscompras.models.personas;

import com.frankester.mscompras.models.compra.mediosDePago.MedioDePago;
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


    public void agregarMedioDePago(MedioDePago medioDePago){
        this.mediosDePago.add(medioDePago);
    }


}
