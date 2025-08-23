package com.frankester.mscompras.models.personas.contacto;

import com.frankester.mscompras.models.Persistence;
import com.frankester.mscompras.models.personas.Persona;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Mensaje extends Persistence {

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "fecha_enviado") // Asegura coincidencia con el DER
    private LocalDateTime fechaDeEnvio;

    @ManyToOne
    @JoinColumn(name = "id_persona_destinatario", nullable = false) // Define explicitamente la FK
    private Persona destinatario;

    public Mensaje(String titulo, String contenido, Persona destinatario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.destinatario = destinatario;
        this.fechaDeEnvio = LocalDateTime.now(); // Define fecha actual al enviar el mensaje
    }
}
