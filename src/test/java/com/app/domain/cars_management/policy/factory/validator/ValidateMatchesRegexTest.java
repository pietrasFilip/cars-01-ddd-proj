package com.app.domain.cars_management.policy.factory.validator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.app.domain.cars_management.policy.factory.validator.DataValidator.validateMatchesRegex;


class ValidateMatchesRegexTest {

    @Test
    @DisplayName("When model is null or empty")
    void test1() {
        var regex = "[A-Z]";
        var model = "";
        Assertions.assertThatThrownBy(() -> validateMatchesRegex(regex ,model))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Model is null or empty");
    }

    @Test
    @DisplayName("When model does not match regex")
    void test2() {
        var regex = "[A-Z]";
        var model = "Audi";
        Assertions.assertThatThrownBy(() -> validateMatchesRegex(regex ,model))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Wrong input format - only uppercase!");
    }
}
