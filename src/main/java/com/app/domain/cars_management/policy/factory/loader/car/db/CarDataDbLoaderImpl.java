package com.app.domain.cars_management.policy.factory.loader.car.db;

import com.app.domain.cars_management.model.repository.db.CarRepositoryDb;
import com.app.infrastructure.persistence.entity.CarEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CarDataDbLoaderImpl implements CarDataDbLoader{
    private final CarRepositoryDb carRepository;

    @Override
    public List<CarEntity> load() {
        return carRepository.findAll();
    }
}
