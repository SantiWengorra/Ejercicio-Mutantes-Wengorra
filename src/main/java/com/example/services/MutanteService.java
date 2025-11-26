package com.example.services;

import com.example.config.AuditContextHolder;
import com.example.controllers.MutanteRequest;
import com.example.repositories.entities.Mutante;
import com.example.repositories.entities.EstadisticasDTO;
import com.example.exceptions.ArgumentoNoValidoException;
import com.example.exceptions.MutanteNoEncontradoException;
import com.example.repositories.MutanteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MutanteService implements BaseService<Mutante> {

    private static final int SEQ_LENGTH = 4;
    private static final Set<String> MUTANT_SEQUENCES = Set.of("AAAA", "TTTT", "CCCC", "GGGG");

    private final MutanteRepository mutanteRepository;

    public MutanteService(MutanteRepository mutanteRepository) {
        this.mutanteRepository = mutanteRepository;
    }

    @Transactional
    public boolean analyze(Mutante persona) {
        validarEntradaADN(persona.getAdn());

        boolean esMutante = isMutant(persona.getAdn());
        persona.setEsMutante(esMutante);

        AuditContextHolder.setOperacion("CREATE");

        mutanteRepository.save(persona);

        return esMutante;
    }

    @Override
    public List<Mutante> findAll() {
        return mutanteRepository.findAll();
    }

    @Override
    public Mutante findById(Long id) {
        return mutanteRepository.findById(id)
                .orElseThrow(() -> new MutanteNoEncontradoException("Mutante con id: " + id + " no encontrado"));
    }

    @Override
    public Mutante update(Long id, Mutante data) {
        Mutante existente = findById(id);

        existente.setNombre(data.getNombre());
        existente.setApellido(data.getApellido());
        existente.setAdn(data.getAdn());

        AuditContextHolder.setOperacion("UPDATE");

        return mutanteRepository.save(existente);
    }

    @Override
    public void delete(Long id) {
        if (!mutanteRepository.existsById(id)) {
            throw new MutanteNoEncontradoException("Mutante con id: " + id + " no encontrado");
        }

        AuditContextHolder.setOperacion("DELETE");

        mutanteRepository.deleteById(id);
    }

    // Verificar ADN

    protected boolean isMutant(List<String> adn) {
        int totalSecuencias = countSecuencias(adn);
        return totalSecuencias > 1;
    }

    private int countSecuencias(List<String> adn) {
        int count = 0;
        int n = adn.size();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                count += checkHorizontal(adn, i, j);
                count += checkVertical(adn, i, j);
                count += checkDiagonal(adn, i, j);
                count += checkDiagonalInversa(adn, i, j);

                if (count > 1) return count;
            }
        }
        return count;
    }

    private int checkHorizontal(List<String> adn, int row, int col) {
        if (col + SEQ_LENGTH > adn.size()) return 0;
        return MUTANT_SEQUENCES.contains(adn.get(row).substring(col, col + SEQ_LENGTH)) ? 1 : 0;
    }

    private int checkVertical(List<String> adn, int row, int col) {
        if (row + SEQ_LENGTH > adn.size()) return 0;

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < SEQ_LENGTH; k++) sb.append(adn.get(row + k).charAt(col));

        return MUTANT_SEQUENCES.contains(sb.toString()) ? 1 : 0;
    }

    private int checkDiagonal(List<String> adn, int row, int col) {
        if (row + SEQ_LENGTH > adn.size() || col + SEQ_LENGTH > adn.size()) return 0;

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < SEQ_LENGTH; k++) sb.append(adn.get(row + k).charAt(col + k));

        return MUTANT_SEQUENCES.contains(sb.toString()) ? 1 : 0;
    }

    private int checkDiagonalInversa(List<String> adn, int row, int col) {
        if (row + SEQ_LENGTH > adn.size() || col - (SEQ_LENGTH - 1) < 0) return 0;

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < SEQ_LENGTH; k++) sb.append(adn.get(row + k).charAt(col - k));

        return MUTANT_SEQUENCES.contains(sb.toString()) ? 1 : 0;
    }

    private void validarEntradaADN(List<String> adn) {
        if (adn == null || adn.isEmpty()) {
            throw new ArgumentoNoValidoException("ADN invalido");
        }

        adn.replaceAll(String::toUpperCase);

        int n = adn.size();

        for (String fila : adn) {
            if (fila == null || fila.length() != n) {
                throw new ArgumentoNoValidoException("El ADN debe ser una matriz NxN");
            }
            if (!fila.matches("[ACGT]+")) {
                throw new ArgumentoNoValidoException("ADN contiene caracteres invalidos");
            }
        }
    }

    public EstadisticasDTO obtenerEstadisticas() {
        long mutantes = mutanteRepository.countByEsMutante(true);
        long humanos = mutanteRepository.countByEsMutante(false);
        double ratio = humanos == 0 ? 0 : (double) mutantes / humanos;
        return new EstadisticasDTO(mutantes, humanos, ratio);
    }
}
