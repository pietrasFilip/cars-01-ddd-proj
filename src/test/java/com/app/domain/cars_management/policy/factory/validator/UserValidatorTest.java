package com.app.domain.cars_management.policy.factory.validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.application.validator.user.generic.UserDtoValidator.validateNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserValidatorTest {

    @Test
    @DisplayName("When is null")
    void test1() {
        assertThatThrownBy(() -> validateNull(null, ""))
                .hasMessage("Is null or empty")
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("When is empty")
    void test2() {
        assertThatThrownBy(() -> validateNull("", ""))
                .hasMessage("Is null or empty")
                .isInstanceOf(IllegalStateException.class);
    }
}
