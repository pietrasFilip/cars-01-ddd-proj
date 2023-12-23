package com.app.domain.cars_management.policy.factory.loader.car.json;

import com.app.domain.cars_management.policy.factory.loader.DataLoader;
import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public interface CarDataJsonLoader extends DataLoader<List<CarEntity>> {
}
