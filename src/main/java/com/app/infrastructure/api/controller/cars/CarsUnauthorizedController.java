package com.app.infrastructure.api.controller.cars;

import com.app.application.dto.response.ResponseDto;
import com.app.application.service.cars.provider.CarsProvider;
import com.app.domain.cars_management.model.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsUnauthorizedController {
    private final CarsProvider carsProvider;

    @GetMapping
    public ResponseDto<List<Car>> getAllCars() {
        return new ResponseDto<>(carsProvider.provide());
    }
}
