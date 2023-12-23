package com.app.domain.cars_management.model.repository.txt;

import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public interface CarRepositoryTxt {
    List<CarEntity> findAll();
}
