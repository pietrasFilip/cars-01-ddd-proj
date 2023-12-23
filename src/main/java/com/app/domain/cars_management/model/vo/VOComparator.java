package com.app.domain.cars_management.model.vo;

import com.app.domain.cars_management.model.Car;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface VOComparator {
    Comparator<Map.Entry<Price, List<Car>>> entryByPrice = Comparator.comparing(entry -> entry.getKey().value);
}
