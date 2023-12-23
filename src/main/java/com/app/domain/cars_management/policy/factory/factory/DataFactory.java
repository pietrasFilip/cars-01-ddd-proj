package com.app.domain.cars_management.policy.factory.factory;


import com.app.domain.cars_management.policy.factory.converter.Converter;
import com.app.domain.cars_management.policy.factory.loader.DataLoader;
import com.app.domain.cars_management.policy.factory.validator.DataValidator;

public interface DataFactory <T, U>{
    DataLoader<T> createDataLoader();
    DataValidator<T> createValidator();
    Converter<T, U> createConverter();
}
