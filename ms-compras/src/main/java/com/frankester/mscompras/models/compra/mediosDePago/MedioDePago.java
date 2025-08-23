package com.frankester.mscompras.models.compra.mediosDePago;

import com.frankester.mscompras.models.Persistence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioDePago extends Persistence {

    public abstract void pagar();

}
