package com.app.domain.cars_management.policy.factory.loader.car.json;

import com.app.domain.cars_management.model.repository.json.CarRepositoryJson;
import com.app.infrastructure.persistence.entity.CarEntity;
import com.app.infrastructure.persistence.repository.impl.json.generic.FromJsonToObjectLoader;
import com.google.gson.Gson;

import java.util.List;

public class CarDataJsonLoaderImpl extends FromJsonToObjectLoader<List<CarEntity>> implements CarDataJsonLoader {
    private final CarRepositoryJson carRepositoryJson;
    public CarDataJsonLoaderImpl(Gson gson, CarRepositoryJson carRepositoryJson) {
        super(gson);
        this.carRepositoryJson = carRepositoryJson;
    }

    @Override
    public List<CarEntity> load() {
        return carRepositoryJson.findAll();
    }
}
