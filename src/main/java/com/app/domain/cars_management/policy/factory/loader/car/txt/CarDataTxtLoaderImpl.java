package com.app.domain.cars_management.policy.factory.loader.car.txt;

import com.app.domain.cars_management.model.repository.txt.CarRepositoryTxt;
import com.app.infrastructure.persistence.entity.CarEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CarDataTxtLoaderImpl implements CarDataTxtLoader {
    private final CarRepositoryTxt carRepositoryTxt;
    @Override
    public List<CarEntity> load() {
        return carRepositoryTxt.findAll();
    }
}
