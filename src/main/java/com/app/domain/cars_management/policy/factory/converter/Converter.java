package com.app.domain.cars_management.policy.factory.converter;

public interface Converter <T, U>{
    U convert(T data);
}
