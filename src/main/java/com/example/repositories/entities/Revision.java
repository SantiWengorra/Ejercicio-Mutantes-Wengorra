package com.example.repositories.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.example.config.CustomRevisionListener;

@Entity
@RevisionEntity(CustomRevisionListener.class)
public class Revision {

    @Id
    @GeneratedValue
    @RevisionNumber
    private Long id;

    @RevisionTimestamp
    private Long marcaTiempo;

    @Column(name = "accion_realizada")
    private String operacion;

    public Long getId() {
        return id;
    }

    public Long getMarcaTiempo() {
        return marcaTiempo;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
}

