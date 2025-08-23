package com.frankester.mscompras.models.personas.documentos;

import com.frankester.mscompras.models.Persistence;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Documento extends Persistence {

    @Enumerated(EnumType.STRING)
    private TipoDeDocumento tipoDeDocumento;

    private String numeroDeDocumento;

}
