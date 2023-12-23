package com.app.application.service.cars;

import com.app.application.service.cars.provider.CarsProvider;
import com.app.application.service.cars_statistics.BigDecimalStatistics;
import com.app.application.service.cars_statistics.NumbersStatisticsType;
import com.app.application.service.cars_statistics.collector.BigDecimalSummaryStatistics;
import com.app.application.service.sort.AbstractSortServiceImpl;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import com.app.domain.cars_management.model.color.Color;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.app.domain.cars_management.model.CarComparator.byModel;
import static com.app.domain.cars_management.model.CarMapper.*;
import static com.app.domain.cars_management.model.vo.VOComparator.entryByPrice;
import static com.app.domain.cars_management.model.vo.VOMapper.toMileageValue;
import static com.app.domain.cars_management.model.vo.VOMapper.toPriceValue;
import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public final class CarsServiceImpl extends AbstractSortServiceImpl<Car> implements CarsService {

    public CarsServiceImpl(CarsProvider dataProcessor) {
        super(dataProcessor);
    }

    /**
     *
     * @param referenceMileage Min mileage from which cars will be selected.
     * @return                 List of cars with mileage greater than reference mileage.
     */
    @Override
    public List<Car> getCarsWithMileageGreaterThan(int referenceMileage) {
        if (referenceMileage < 0) {
            throw new IllegalStateException("Reference mileage is less than zero");
        }

        return cars
                .stream()
                .filter(car -> car.hasMileageGreaterThan(referenceMileage))
                .toList();
    }

    /**
     *
     * @return Map of color and number of cars that has specified color.
     */
    @Override
    public Map<Color, Integer> getNumberOfCarsWithColors() {
        return carsWithColors()
                .entrySet()
                .stream()
                .sorted(reverseOrder(comparingByValue()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (c1, c2) -> c1,
                        LinkedHashMap::new
                ));
    }

    private Map<Color, Integer> carsWithColors() {
        return cars
                .stream()
                .collect(groupingBy(convertToColor))
                .entrySet()
                .stream()
                .collect(toMap(Map.Entry::getKey, value -> value.getValue().size()));
    }

    /**
     *
     * @return Map of max priced car model name and list of these cars.
     */
    @Override
    public Map<String, List<Car>> getMaxPriceCars() {
        return maxPricedCarModels()
                .entrySet()
                .stream()
                .sorted(reverseOrder(comparingByKey()))
                .collect(toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (c1, c2) -> c1,
                        LinkedHashMap::new
                ));
    }

    private Map<String, List<Car>> maxPricedCarModels() {
        return cars
                .stream()
                .collect(groupingBy(convertToModel,
                        Collectors.collectingAndThen(
                                groupingBy(convertToPrice),
                                groupedByPrice -> groupedByPrice
                                        .entrySet()
                                        .stream()
                                        .max(entryByPrice)
                                        .map(Map.Entry::getValue)
                                        .orElseThrow()
                        )));
    }

    /**
     *
     * @param numbersStatisticsType Type of statistic to return.
     * @return                      Min, average, max, sum and count of NumberStatisticsType.
     */
    @Override
    public BigDecimalStatistics getCarNumberStatistics(NumbersStatisticsType numbersStatisticsType) {
        return switch (numbersStatisticsType) {
            case PRICE -> cars
                    .stream()
                    .map(convertToPrice)
                    .map(toPriceValue)
                    .collect(new BigDecimalSummaryStatistics());
            case MILEAGE -> cars
                    .stream()
                    .map(convertToMileage)
                    .map(toMileageValue)
                    .map(BigDecimal::new)
                    .collect(new BigDecimalSummaryStatistics());
        };
    }

    /**
     *
     * @return List of most expensive cars.
     */
    @Override
    public List<Car> getMostExpensiveCar() {
        return cars
                .stream()
                .collect(groupingBy(convertToPrice))
                .entrySet()
                .stream()
                .max(entryByPrice)
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    /**
     *
     * @param componentsComparator Car components comparator.
     * @return                     List of cars with alphabetically sorted components.
     */
    @Override
    public List<Car> sortComponentsAlphabetically(Comparator<Component> componentsComparator) {
        return cars
                .stream()
                .map(car -> car.withSortedComponents(componentsComparator))
                .toList();
    }

    /**
     *
     * @return Map of components and cars containing these components.
     */
    @Override
    public Map<Component, List<Car>> getNumberOfCarsWithComponent() {
        return cars
                .stream()
                .flatMap(convertToComponentsAsStream)
                .distinct()
                .collect(toMap(Function.identity(), this::hasComponent))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (x1, x2) -> x1, LinkedHashMap::new));
    }

    private List<Car> hasComponent(Component component) {
        return cars
                .stream()
                .filter(car -> car.hasComponent(component))
                .toList();
    }

    /**
     *
     * @param from Min cars price.
     * @param to   Max cars price.
     * @return     List of cars with prices between from and to.
     */
    @Override
    public List<Car> getCarsWithPriceBetween(BigDecimal from, BigDecimal to) {
        if (from.compareTo(to) > 0) {
            throw new IllegalStateException("Wrong price range");
        }

        var carsWithPrice = cars
                .stream()
                .filter(car -> car.hasPriceBetween(from, to))
                .sorted(byModel)
                .toList();

        if (carsWithPrice.isEmpty()) {
            throw new IllegalStateException("No cars with price between");
        }

        return carsWithPrice;
    }

}
