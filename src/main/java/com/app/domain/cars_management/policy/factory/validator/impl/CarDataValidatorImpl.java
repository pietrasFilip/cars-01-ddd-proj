package com.app.domain.cars_management.policy.factory.validator.impl;

import com.app.domain.cars_management.policy.factory.validator.CarDataValidator;
import com.app.infrastructure.persistence.entity.CarEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.app.domain.cars_management.policy.factory.validator.DataValidator.*;


@RequiredArgsConstructor
public class CarDataValidatorImpl implements CarDataValidator {
    private final String modelNameRegex;
    private final String itemsNameRegex;
    @Override
    public List<CarEntity> validate(List<CarEntity> carData) {
        return carData
                .stream()
                .map(this::validateSingleCar)
                .toList();
    }

    @Override
    public CarEntity validateSingleCar(CarEntity carData) {
        return CarEntity
                .builder()
                .id(carData.getId())
                .model(validateMatchesRegex(modelNameRegex, carData.getModel()))
                .price(carData.getPrice())
                .color(validateNull(carData.getColor()))
                .mileage(carData.getMileage())
                .components(validateItemsMatchesRegex(itemsNameRegex, carData.getComponents()))
                .build();
    }
}
