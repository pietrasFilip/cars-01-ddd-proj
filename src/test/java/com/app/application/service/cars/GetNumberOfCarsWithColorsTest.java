package com.app.application.service.cars;

import com.app.config.AppTestConfig;
import com.app.domain.cars_management.model.color.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
class GetNumberOfCarsWithColorsTest {
    @Autowired
    private CarsService carsService;

    @TestFactory
    @DisplayName("When cars are not sorted descending")
    Stream<DynamicNode> test1() {
        var expected = new LinkedHashMap<Color, Integer>();
        expected.put(Color.BLUE, 2);
        expected.put(Color.WHITE, 1);

        return Stream.of(carsService.getNumberOfCarsWithColors())
                .map(n -> dynamicContainer("Container", Stream.of(
                        dynamicTest("Not empty",
                                () -> assertThat(n).isNotEmpty()),
                        dynamicTest("Is equal to expected",
                                () -> assertThat(n).containsExactlyEntriesOf(expected))
                )));
    }
}
