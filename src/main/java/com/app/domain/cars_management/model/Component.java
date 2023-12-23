package com.app.domain.cars_management.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Builder
public class Component {
    final Long id;
    final String name;

    public Component(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Methods from Object class

    @Override
    public String toString() {
        return "COMPONENT: %s".formatted(name);
    }

    // Static methods

    public static Component of(Long id, String name) {
        return new Component(id, name);
    }
}
