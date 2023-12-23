package com.app.domain.cars_management.policy.factory.converter;


import com.app.domain.cars_management.model.Car;
import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public class ToCarConverterImpl implements ToCarConverter {
    @Override
    public List<Car> convert(List<CarEntity> data) {
        return data
                .stream()
                .map(CarEntity::toDomain)
                .toList();
    }
}
