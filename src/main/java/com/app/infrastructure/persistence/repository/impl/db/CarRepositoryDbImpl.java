package com.app.infrastructure.persistence.repository.impl.db;

import com.app.domain.cars_management.model.repository.db.CarRepositoryDb;
import com.app.infrastructure.persistence.entity.CarEntity;
import com.app.infrastructure.persistence.repository.impl.db.dao.CarEntityDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CarRepositoryDbImpl implements CarRepositoryDb {
    private final CarEntityDao carEntityDbDao;

    @Override
    public List<CarEntity> findAll() {
        return carEntityDbDao
                .findAll();
    }
}
