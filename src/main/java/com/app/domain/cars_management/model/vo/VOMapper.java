package com.app.domain.cars_management.model.vo;


import java.math.BigDecimal;
import java.util.function.Function;

public interface VOMapper {
    Function<Price, BigDecimal> toPriceValue = price -> price.value;
    Function<Mileage, Integer> toMileageValue = mileage -> mileage.value;
}
