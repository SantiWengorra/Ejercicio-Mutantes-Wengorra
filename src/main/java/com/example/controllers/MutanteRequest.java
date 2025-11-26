package com.example.controllers;

import lombok.Data;

import java.util.List;

@Data
public class MutanteRequest {
    private String nombre;
    private String apellido;
    private List<String> adn;
}
