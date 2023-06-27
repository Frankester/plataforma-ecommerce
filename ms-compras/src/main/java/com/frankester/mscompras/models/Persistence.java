package com.frankester.mscompras.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public class Persistence {

    @Id
    @GeneratedValue
    private Long id;
}
