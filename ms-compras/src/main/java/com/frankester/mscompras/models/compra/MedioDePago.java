package com.frankester.mscompras.models.compra;

import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.personas.Vendedor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class MedioDePago extends Persistence {

    @Column(name="medio_pago")
    private String medioDePago;


}
