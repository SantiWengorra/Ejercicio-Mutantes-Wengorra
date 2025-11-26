package com.example.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EstadisticasDTO {

    private long totalMutantes;
    private long totalHumanos;
    private double proporcion;
}
