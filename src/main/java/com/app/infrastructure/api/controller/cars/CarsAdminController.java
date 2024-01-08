package com.app.infrastructure.api.controller.cars;

import com.app.application.dto.response.ResponseDto;
import com.app.application.service.cars.CarsService;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.app.domain.cars_management.model.ComponentComparator.byComponentName;

@RestController
@RequestMapping("/admin/cars")
@RequiredArgsConstructor
public class CarsAdminController {
    private final CarsService carsService;

    @GetMapping("/components/sort/alphabetically/asc")
    public ResponseDto<List<Car>> sortComponentsAlphabeticallyAscending() {
        return new ResponseDto<>(carsService.sortComponentsAlphabetically(byComponentName));
    }

    @GetMapping("/components/sort/alphabetically/desc")
    public ResponseDto<List<Car>> sortComponentsAlphabeticallyDescending() {
        return new ResponseDto<>(carsService.sortComponentsAlphabetically(byComponentName)
                .reversed());
    }

    @GetMapping("/with-component")
    public ResponseDto<Map<Component, List<Car>>> getComponentsWithCars() {
        return new ResponseDto<>(carsService.getNumberOfCarsWithComponent());
    }
}
