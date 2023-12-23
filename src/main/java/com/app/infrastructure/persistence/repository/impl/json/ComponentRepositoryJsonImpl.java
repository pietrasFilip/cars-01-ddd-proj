package com.app.infrastructure.persistence.repository.impl.json;

import com.app.domain.cars_management.model.repository.json.ComponentRepositoryJson;
import com.app.infrastructure.persistence.repository.impl.json.generic.FromJsonToObjectLoader;
import com.app.infrastructure.persistence.entity.ComponentEntity;
import com.google.gson.Gson;

import java.util.List;

public class ComponentRepositoryJsonImpl extends FromJsonToObjectLoader<List<ComponentEntity>> implements ComponentRepositoryJson {
    private final List<ComponentEntity> components;

    public ComponentRepositoryJsonImpl(Gson gson, String filename) {
        super(gson);
        this.components = init(filename);
    }

    @Override
    public List<ComponentEntity> findAll() {
        return components;
    }

    @Override
    public List<ComponentEntity> findAllById(List<Long> ids) {
        return components
                .stream()
                .filter(component -> ids.contains(component.getId()))
                .toList();
    }

    private List<ComponentEntity> init(String filename) {
        return loadObject(filename);
    }
}
