package com.app.application.service.cars;


import com.app.application.service.cars_statistics.BigDecimalStatistics;
import com.app.application.service.cars_statistics.NumbersStatisticsType;
import com.app.application.service.sort.SortService;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import com.app.domain.cars_management.model.color.Color;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public sealed interface CarsService extends SortService<Car> permits CarsServiceImpl{
    List<Car> getCarsWithMileageGreaterThan(int referenceMileage);
    Map<Color, Integer> getNumberOfCarsWithColors();
    Map<String, List<Car>> getMaxPriceCars();
    BigDecimalStatistics getCarNumberStatistics(NumbersStatisticsType numbersStatisticsType);
    List<Car> getMostExpensiveCar();
    List<Car> sortComponentsAlphabetically(Comparator<Component> componentsComparator);
    Map<Component, List<Car>> getNumberOfCarsWithComponent();
    List<Car> getCarsWithPriceBetween(BigDecimal from, BigDecimal to);
}
