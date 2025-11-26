package com.example.controllers;

import com.example.controllers.MutanteRequest;
import com.example.controllers.RespuestaMutanteDTO;
import com.example.repositories.entities.Mutante;
import com.example.repositories.entities.EstadisticasDTO;
import com.example.services.MutanteService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/mutant/")
public class MutanteController {

    private final MutanteService mutanteService;

    public MutanteController(MutanteService mutanteService) {
        this.mutanteService = mutanteService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<Mutante> mutantes = mutanteService.findAll();
        return ResponseEntity.ok(mutantes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Mutante mutante = mutanteService.findById(id);
        return ResponseEntity.ok(mutante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody Mutante entity) {

        Mutante mutante = mutanteService.update(id, entity);
        return ResponseEntity.ok(mutante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        mutanteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/analizar")
    public ResponseEntity<?> analyze(@RequestBody MutanteRequest request) {

        // mapear DTO a entidad SIMPLE
        Mutante persona = new Mutante();
        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setAdn(request.getAdn());

        boolean esMutante = mutanteService.analyze(persona);

        String mensaje = esMutante ?
                "La persona es un mutante" :
                "La persona no es un mutante";

        return ResponseEntity.ok(
                new RespuestaMutanteDTO(esMutante, mensaje)
        );
    }

    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasDTO> obtenerEstadisticas() {
        return ResponseEntity.ok(mutanteService.obtenerEstadisticas());
    }
}
