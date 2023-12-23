package com.app.domain.cars_management.policy.factory.processor.impl;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.policy.factory.converter.Converter;
import com.app.domain.cars_management.policy.factory.factory.FromTxtToCarWithValidator;
import com.app.domain.cars_management.policy.factory.loader.DataLoader;
import com.app.domain.cars_management.policy.factory.processor.CarDataProcessor;
import com.app.domain.cars_management.policy.factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public class CarDataProcessorTxtImpl implements CarDataProcessor {
    private final DataLoader<List<CarEntity>> dataLoader;
    private final DataValidator<List<CarEntity>> validator;
    private final Converter<List<CarEntity>, List<Car>> converter;

    public CarDataProcessorTxtImpl(FromTxtToCarWithValidator dataFactory) {
        this.dataLoader = dataFactory.createDataLoader();
        this.validator = dataFactory.createValidator();
        this.converter = dataFactory.createConverter();
    }
    @Override
    public List<Car> process() {
        var loadedData = dataLoader.load();
        var validatedData = validator.validate(loadedData);
        return converter.convert(validatedData);
    }
}
