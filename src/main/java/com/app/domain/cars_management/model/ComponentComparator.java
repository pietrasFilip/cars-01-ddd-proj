package com.app.domain.cars_management.model;

import java.util.Comparator;

public interface ComponentComparator {
    Comparator<Component> byComponentName = Comparator.comparing(component -> component.name);
}
