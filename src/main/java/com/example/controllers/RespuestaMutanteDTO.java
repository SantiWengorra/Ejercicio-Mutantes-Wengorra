package com.example.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RespuestaMutanteDTO {
    private boolean esMutante;
    private String mensaje;
}
