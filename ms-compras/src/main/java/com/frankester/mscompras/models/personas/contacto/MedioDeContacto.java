package com.frankester.mscompras.models.personas.contacto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "discriminator")
public abstract class MedioDeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private int idMedioDeContacto;

    public abstract void notificar(Mensaje mensaje) ;

    protected boolean esAmbienteDePrueba() {
        return System.getProperty("env") != null && System.getProperty("env").equals("test");
    }

}
