package com.app.infrastructure.persistence.repository.impl.txt;

import com.app.domain.cars_management.model.repository.txt.ComponentRepositoryTxt;
import com.app.infrastructure.persistence.entity.ComponentEntity;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ComponentRepositoryTxtImpl implements ComponentRepositoryTxt {
    private final List<ComponentEntity> components;

    public ComponentRepositoryTxtImpl(String filename) {
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

    private static List<ComponentEntity> init(String filename) {
        try (var lines = Files.lines(Paths.get(filename))) {
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        return ComponentEntity
                                .builder()
                                .id(Long.valueOf(items[0]))
                                .name(items[1])
                                .build();
                    })
                    .toList();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
