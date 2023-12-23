package com.app.infrastructure.persistence.repository.impl.db.dao.impl;

import com.app.infrastructure.persistence.entity.ComponentEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.ComponentEntityDao;
import com.app.infrastructure.persistence.repository.impl.db.dao.generic.AbstractCrudRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ComponentEntityDaoImpl extends AbstractCrudRepository<ComponentEntity, Long> implements ComponentEntityDao {
    public ComponentEntityDaoImpl(EntityManagerFactory emf) {
        super(emf);
    }
}
