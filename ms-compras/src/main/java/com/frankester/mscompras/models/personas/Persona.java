package com.frankester.mscompras.models.personas;

import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.personas.contacto.MedioDeContacto;
import com.frankester.mscompras.models.personas.documentos.Documento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona extends Persistence {

    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Documento documento;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name ="id_persona")
    private List<MedioDeContacto> mediosDeContacto;

    public Persona() {
        this.mediosDeContacto = new ArrayList<>();
    }

    public void agregarMedioDeContacto(MedioDeContacto medioDeContacto){
        this.mediosDeContacto.add(medioDeContacto);
    }

}
