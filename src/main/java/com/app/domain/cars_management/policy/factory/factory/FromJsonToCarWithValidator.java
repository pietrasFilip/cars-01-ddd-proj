package com.app.domain.cars_management.policy.factory.factory;

import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.repository.json.CarRepositoryJson;
import com.app.domain.cars_management.policy.factory.converter.Converter;
import com.app.domain.cars_management.policy.factory.converter.ToCarConverterImpl;
import com.app.domain.cars_management.policy.factory.loader.DataLoader;
import com.app.domain.cars_management.policy.factory.loader.car.json.CarDataJsonLoaderImpl;
import com.app.domain.cars_management.policy.factory.validator.CarDataValidator;
import com.app.domain.cars_management.policy.factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.CarEntity;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromJsonToCarWithValidator implements DataFactory<List<CarEntity>, List<Car>> {
    private final CarRepositoryJson carRepositoryJson;
    private final Gson gson;
    private final CarDataValidator carDataValidator;
    @Override
    public DataLoader<List<CarEntity>> createDataLoader() {
        return new CarDataJsonLoaderImpl(gson, carRepositoryJson);
    }

    @Override
    public DataValidator<List<CarEntity>> createValidator() {
        return carDataValidator;
    }

    @Override
    public Converter<List<CarEntity>, List<Car>> createConverter() {
        return new ToCarConverterImpl();
    }
}
