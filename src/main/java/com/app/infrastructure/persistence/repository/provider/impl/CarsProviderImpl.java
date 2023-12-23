package com.app.infrastructure.persistence.repository.provider.impl;

import com.app.application.service.cars.provider.CarsProvider;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.policy.factory.processor.CarDataProcessor;
import com.app.domain.cars_management.policy.factory.processor.type.ProcessorType;
import com.app.infrastructure.persistence.repository.provider.generic.AbstractDataProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CarsProviderImpl extends AbstractDataProvider<Car> implements CarsProvider {

    @Value("${processor.type}")
    private String processorType;
    private final CarDataProcessor carDataDbProcessor;
    private final CarDataProcessor carDataJsonProcessor;
    private final CarDataProcessor carDataTxtProcessor;

    @Override
    public List<Car> provide() {
        return switch (ProcessorType.valueOf(processorType)) {
            case FROM_DB_TO_CAR_WITH_VALIDATOR -> carDataDbProcessor.process();
            case FROM_JSON_TO_CAR_WITH_VALIDATOR -> carDataJsonProcessor.process();
            case FROM_TXT_TO_CAR_WITH_VALIDATOR -> carDataTxtProcessor.process();
        };
    }
}
