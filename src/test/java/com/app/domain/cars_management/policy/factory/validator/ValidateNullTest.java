package com.app.domain.cars_management.policy.factory.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.domain.cars_management.policy.factory.validator.DataValidator.validateNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ValidateNullTest {
    @Test
    @DisplayName("When is null")
    void test1() {
        assertThatThrownBy(() -> validateNull(null))
                .hasMessage("Is null")
                .isInstanceOf(IllegalStateException.class);
    }
}
