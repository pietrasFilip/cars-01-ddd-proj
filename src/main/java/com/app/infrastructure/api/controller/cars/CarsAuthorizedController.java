package com.app.infrastructure.api.controller.cars;

import com.app.application.dto.response.ResponseDto;
import com.app.application.service.cars.CarsService;
import com.app.application.service.cars_statistics.BigDecimalStatistics;
import com.app.application.service.cars_statistics.NumbersStatisticsType;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.color.Color;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth/cars")
@RequiredArgsConstructor
public class CarsAuthorizedController {
    private final CarsService carsService;

    @GetMapping("/mileage/greater-than/{mileageFrom}")
    public ResponseDto<List<Car>> getCarsWithMileageGreaterThan(@PathVariable int mileageFrom) {
        return new ResponseDto<>(carsService.getCarsWithMileageGreaterThan(mileageFrom));
    }

    @GetMapping("/color-count")
    public ResponseDto<Map<Color, Integer>> getCarColorCount() {
        return new ResponseDto<>(carsService.getNumberOfCarsWithColors());
    }

    @GetMapping("/price/max")
    public ResponseDto<Map<String, List<Car>>> getMaxPriceCars() {
        return new ResponseDto<>(carsService.getMaxPriceCars());
    }

    @GetMapping("/statistics/type/{statisticType}")
    public ResponseDto<BigDecimalStatistics> getCarsStatistics(@PathVariable NumbersStatisticsType statisticType) {
        return new ResponseDto<>(carsService.getCarNumberStatistics(statisticType));
    }

    @GetMapping("/most-expensive")
    public ResponseDto<List<Car>> getMostExpensiveCar() {
        return new ResponseDto<>(carsService.getMostExpensiveCar());
    }

    @GetMapping("/price/from/{priceFrom}/to/{priceTo}")
    public ResponseDto<List<Car>> getCarsWithPriceBetween(@PathVariable BigDecimal priceFrom,
                                                          @PathVariable BigDecimal priceTo) {
        return new ResponseDto<>(carsService.getCarsWithPriceBetween(priceFrom, priceTo));
    }
}
