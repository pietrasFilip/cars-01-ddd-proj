package com.app.domain.cars_management.policy.factory.validator;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.domain.cars_management.policy.factory.validator.DataValidator.validatePositiveInt;


class ValidatePositiveIntTest {
    @Test
    @DisplayName("When mileage is less than zero")
    void test1() {
        var mileage = -100;
        Assertions.assertThatThrownBy(() -> validatePositiveInt(mileage))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Mileage less than zero");
    }
}
