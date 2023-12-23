package com.app.infrastructure.persistence.repository.impl.db.dao.generic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Component;

@Component
public class EntityManagerWrapper implements AutoCloseable {
    private final EntityManager entityManager;

    public EntityManagerWrapper(EntityManagerFactory emf) {
        this.entityManager = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void close() throws Exception {
        if (entityManager != null) {
            entityManager.close();
        }
    }
}
