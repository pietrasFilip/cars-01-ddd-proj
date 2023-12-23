package com.app.application.service.cars;

import com.app.config.AppTestConfig;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import com.app.domain.cars_management.model.vo.Mileage;
import com.app.domain.cars_management.model.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import static com.app.domain.cars_management.model.color.Color.BLUE;
import static com.app.domain.cars_management.model.color.Color.WHITE;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class GetNumberOfCarsWithComponentTest {
    @Autowired
    private CarsService carsService;

    @Test
    @DisplayName("When is not sorted descending by number of cars with specified component")
    void test1() {
        var carsWithComponent = carsService.getNumberOfCarsWithComponent();
        var expected = new LinkedHashMap<Component, List<Car>>();
        expected.put(Component.of(2L, "B"), List.of(
                Car.of(1L, "BMW", new Price("20"), WHITE, new Mileage(200),
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A"))),
                Car.of(3L, "KIA", new Price("2000"), BLUE, new Mileage(2000),
                        Set.of(Component.of(2L, "B")))
        ));
        expected.put(Component.of(1L, "A"), List.of(
                Car.of(1L, "BMW", new Price("20"), WHITE, new Mileage(200),
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A"))),
                Car.of(2L, "BMW", new Price("2000"), BLUE, new Mileage(2000),
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C")))
        ));
        expected.put(Component.of(3L, "C"), List.of(
                Car.of(2L, "BMW", new Price("2000"), BLUE, new Mileage(2000),
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C")))
        ));

        assertThat(carsWithComponent)
                .isNotEmpty()
                .containsExactlyEntriesOf(expected);
    }
}
