package com.app.domain.cars_management.policy.factory.processor;


import java.util.List;

public interface DataProcessor <T>{
    List<T> process();
}
