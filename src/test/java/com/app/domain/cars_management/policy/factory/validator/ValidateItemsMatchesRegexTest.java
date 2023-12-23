package com.app.domain.cars_management.policy.factory.validator;

import com.app.infrastructure.persistence.entity.ComponentEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.app.domain.cars_management.policy.factory.validator.DataValidator.validateItemsMatchesRegex;


class ValidateItemsMatchesRegexTest {
    @Test
    @DisplayName("When component does not match regex")
    void test1() {
        var regex = "[a-z]";
        var components = List.of(
                ComponentEntity.builder().id(1L).name("wheel").build(),
                ComponentEntity.builder().id(2L).name("SPOILER").build()
        );
        Assertions.assertThatThrownBy(() -> validateItemsMatchesRegex(regex ,components))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Wrong component format");
    }
}
