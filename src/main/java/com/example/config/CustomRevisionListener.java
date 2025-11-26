package com.example.config;

import com.example.repositories.entities.Revision;
import org.hibernate.envers.RevisionListener;

public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Revision rev = (Revision) revisionEntity;
        rev.setOperacion(AuditContextHolder.getOperacion());
    }
}