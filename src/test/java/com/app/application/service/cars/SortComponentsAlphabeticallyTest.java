package com.app.application.service.cars;

import com.app.config.AppTestConfig;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import com.app.domain.cars_management.model.vo.Mileage;
import com.app.domain.cars_management.model.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static com.app.domain.cars_management.model.ComponentComparator.byComponentName;
import static com.app.domain.cars_management.model.color.Color.BLUE;
import static com.app.domain.cars_management.model.color.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class SortComponentsAlphabeticallyTest {
    @Autowired
    private CarsService carsService;

    @TestFactory
    @DisplayName("When components are not sorted alphabetically")
    Stream<DynamicNode> test1() {
        var expected = List.of(
                Car.of(1L, "BMW", new Price("20"), WHITE, new Mileage(200),
                        Set.of(Component.of(1L, "A"), Component.of(2L, "B"))),
                Car.of(2L, "BMW", new Price("2000"), BLUE, new Mileage(2000),
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C"))),
                Car.of(3L, "KIA", new Price("2000"), BLUE, new Mileage(2000),
                        Set.of(Component.of(2L, "B")))
        );

        return Stream.of(carsService.sortComponentsAlphabetically(byComponentName))
                .map(n -> dynamicContainer("Container", Stream.of(
                        dynamicTest("Not empty",
                                () -> assertThat(n).isNotEmpty()),
                        dynamicTest("Is equal to expected",
                                () -> assertThat(n).isEqualTo(expected))
                )));
    }
}
