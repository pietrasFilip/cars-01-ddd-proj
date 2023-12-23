package com.app.domain.cars_management.policy.factory.converter;

import com.app.domain.cars_management.model.Car;
import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public interface ToCarConverter extends Converter<List<CarEntity>, List<Car>> {
}
