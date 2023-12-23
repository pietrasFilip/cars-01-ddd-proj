package com.app.domain.cars_management.model.repository.json;

import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public interface CarRepositoryJson {
    List<CarEntity> findAll();
    List<CarEntity> findAllWithoutComponents();
}
