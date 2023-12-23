package com.app.domain.cars_management.model.repository.db;

import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public interface CarRepositoryDb {
    List<CarEntity> findAll();
}
