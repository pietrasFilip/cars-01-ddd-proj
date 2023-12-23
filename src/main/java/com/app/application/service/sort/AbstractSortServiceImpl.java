package com.app.application.service.sort;

import com.app.infrastructure.persistence.repository.provider.generic.DataProvider;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public abstract class AbstractSortServiceImpl <T> implements SortService<T>{
    protected final List<T> cars;

    protected AbstractSortServiceImpl(DataProvider<T> dataProvider) {
        this.cars = dataProvider.provide();
    }

    @Override
    public List<T> sortBy(Comparator<T> comparator) {
        return cars
                .stream()
                .sorted(comparator)
                .toList();
    }
}