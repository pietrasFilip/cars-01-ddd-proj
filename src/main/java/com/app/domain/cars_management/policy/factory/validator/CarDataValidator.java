package com.app.domain.cars_management.policy.factory.validator;

import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public interface CarDataValidator extends DataValidator<List<CarEntity>> {
    CarEntity validateSingleCar(CarEntity carData);
}
