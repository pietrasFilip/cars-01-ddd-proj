package com.app.domain.cars_management.model;

import com.app.domain.cars_management.model.color.Color;
import com.app.domain.cars_management.model.vo.Mileage;
import com.app.domain.cars_management.model.vo.Price;

import java.util.function.Function;
import java.util.stream.Stream;

public interface CarMapper {
    Function<Car, Color> convertToColor = car -> car.color;
    Function<Car, String> convertToModel = car -> car.model;
    Function<Car, Price> convertToPrice = car -> car.price;
    Function<Car, Mileage> convertToMileage = car -> car.mileage;
    Function<Car, Stream<Component>> convertToComponentsAsStream = car -> car.components.stream();
}
