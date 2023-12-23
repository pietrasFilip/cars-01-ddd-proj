package com.app.infrastructure.persistence.repository.impl.db.dao.impl;

import com.app.infrastructure.persistence.entity.CarEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.CarEntityDao;
import com.app.infrastructure.persistence.repository.impl.db.dao.generic.AbstractCrudRepository;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class CarEntityDaoImpl extends AbstractCrudRepository<CarEntity, Long> implements CarEntityDao {
    public CarEntityDaoImpl(EntityManagerFactory emf) {
        super(emf);
    }
}
