package com.app.domain.cars_management.model.repository.json;

import com.app.infrastructure.persistence.entity.ComponentEntity;

import java.util.List;

public interface ComponentRepositoryJson {
    List<ComponentEntity> findAll();
    List<ComponentEntity> findAllById(List<Long> ids);
}

