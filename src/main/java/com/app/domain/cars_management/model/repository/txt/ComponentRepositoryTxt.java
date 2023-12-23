package com.app.domain.cars_management.model.repository.txt;

import com.app.infrastructure.persistence.entity.ComponentEntity;

import java.util.List;

public interface ComponentRepositoryTxt {
    List<ComponentEntity> findAll();
    List<ComponentEntity> findAllById(List<Long> ids);
}
