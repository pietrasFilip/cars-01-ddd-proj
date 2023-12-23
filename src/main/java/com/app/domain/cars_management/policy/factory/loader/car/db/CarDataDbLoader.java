package com.app.domain.cars_management.policy.factory.loader.car.db;

import com.app.domain.cars_management.policy.factory.loader.DataLoader;
import com.app.infrastructure.persistence.entity.CarEntity;

import java.util.List;

public interface CarDataDbLoader extends DataLoader<List<CarEntity>> {
}
