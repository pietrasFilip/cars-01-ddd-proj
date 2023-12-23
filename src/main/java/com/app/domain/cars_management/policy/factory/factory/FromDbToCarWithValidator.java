package com.app.domain.cars_management.policy.factory.factory;

import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.repository.db.CarRepositoryDb;
import com.app.domain.cars_management.policy.factory.converter.Converter;
import com.app.domain.cars_management.policy.factory.converter.ToCarConverterImpl;
import com.app.domain.cars_management.policy.factory.loader.DataLoader;
import com.app.domain.cars_management.policy.factory.loader.car.db.CarDataDbLoaderImpl;
import com.app.domain.cars_management.policy.factory.validator.CarDataValidator;
import com.app.domain.cars_management.policy.factory.validator.DataValidator;
import com.app.infrastructure.persistence.entity.CarEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FromDbToCarWithValidator implements DataFactory<List<CarEntity>, List<Car>>{
    private final CarRepositoryDb carRepositoryDb;
    private final CarDataValidator carDataValidator;
    @Override
    public DataLoader<List<CarEntity>> createDataLoader() {
        return new CarDataDbLoaderImpl(carRepositoryDb);
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
