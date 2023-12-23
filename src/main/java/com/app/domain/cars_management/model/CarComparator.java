package com.app.domain.cars_management.model;


import java.util.Comparator;

import static com.app.domain.cars_management.model.vo.VOMapper.toMileageValue;
import static com.app.domain.cars_management.model.vo.VOMapper.toPriceValue;

public interface CarComparator {
    Comparator<Car> byModel = Comparator.comparing(car -> car.model);
    Comparator<Car> byPrice = Comparator.comparing(car -> toPriceValue.apply(car.price));
    Comparator<Car> byColor = Comparator.comparing(car -> car.color);
    Comparator<Car> byMileage = Comparator.comparing(car -> toMileageValue.apply(car.mileage));
}
