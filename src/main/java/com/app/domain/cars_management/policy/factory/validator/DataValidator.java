package com.app.domain.cars_management.policy.factory.validator;


import com.app.infrastructure.persistence.entity.ComponentEntity;

import java.math.BigDecimal;
import java.util.List;

public interface DataValidator<T> {
    T validate(T t);

    static String validateMatchesRegex(String regex, String model) {
        if (model == null || model.isEmpty()) {
            throw new IllegalStateException("Model is null or empty");
        }

        if (!model.matches(regex)) {
            throw new IllegalStateException("Wrong input format - only uppercase!");
        }
        return model;
    }

    static BigDecimal validatePositiveDecimal(BigDecimal value) {
        if (value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Price value is zero or less");
        }
        return value;
    }

    static int validatePositiveInt(int value) {
        if (value < 0) {
            throw new IllegalStateException("Mileage less than zero");
        }
        return value;
    }

    static List<ComponentEntity> validateItemsMatchesRegex(String regex, List<ComponentEntity> items) {
        if (items != null) {
            items
                    .forEach(component -> {
                        if (!component.getName().matches(regex)) {
                            throw new IllegalStateException("Wrong component format");
                        }
                    });
        }
        return items;
    }

    static <T> T validateNull(T t) {
        if (t == null) {
            throw new IllegalStateException("Is null");
        }
        return t;
    }
}
