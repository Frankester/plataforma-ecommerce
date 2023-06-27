package com.frankester.mscompras.models.compra;

import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.Publicacion;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.springframework.data.repository.cdi.Eager;

@Data
@Entity
public class Item extends Persistence {

    private int cantidad;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_publicacion", referencedColumnName= "id")
    private Publicacion publicacion;


}
