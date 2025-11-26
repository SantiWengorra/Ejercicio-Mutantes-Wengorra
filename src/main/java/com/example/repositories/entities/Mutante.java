package com.example.repositories.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.List;

@Entity
@Table(name = "persona")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Audited
public class Mutante {

    @Id
    @GeneratedValue
    private Long id;

    private String nombre;

    private String apellido;

    @Column(name = "es_mutante")
    private boolean esMutante;

    @ElementCollection
    @CollectionTable(name = "persona_adn", joinColumns = @JoinColumn(name = "persona_id"))
    @Column(name = "cadena")
    private List<String> adn;
}
