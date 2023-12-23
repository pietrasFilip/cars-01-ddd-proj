package com.app.domain.cars_management.policy.factory.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.app.domain.cars_management.policy.factory.validator.DataValidator.validatePositiveDecimal;


class ValidatePositiveDecimalTest {
    @Test
    @DisplayName("When price is zero")
    void test1() {
        var price = BigDecimal.valueOf(0);
        Assertions.assertThatThrownBy(() -> validatePositiveDecimal(price))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Price value is zero or less");
    }

    @Test
    @DisplayName("When price is less than zero")
    void test2() {
        var price = BigDecimal.valueOf(-20);
        Assertions.assertThatThrownBy(() -> validatePositiveDecimal(price))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Price value is zero or less");
    }
}
