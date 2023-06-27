package com.frankester.msproductobase.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PosiblePersonalizacion {
    
    @Id
    @GeneratedValue
    private Long id;

    private String areaPersonalizacion;
    private String tipoPersonalizacion;


}
