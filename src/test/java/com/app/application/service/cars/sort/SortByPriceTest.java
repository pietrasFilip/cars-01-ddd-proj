package com.app.application.service.cars.sort;

import com.app.application.service.cars.CarsService;
import com.app.config.AppTestConfig;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import com.app.domain.cars_management.model.color.Color;
import com.app.domain.cars_management.model.vo.Mileage;
import com.app.domain.cars_management.model.vo.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static com.app.domain.cars_management.model.CarComparator.byPrice;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
class SortByPriceTest {

    @Autowired
    private CarsService carsService;
    @Test
    @DisplayName("When there is not ascending order")
    void test1() {
        var carListExpected = List.of(
                Car.of(1L, "BMW", new Price("20"), Color.WHITE, new Mileage(200),
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A"))),
                Car.of(2L, "BMW", new Price("2000"), Color.BLUE, new Mileage(2000),
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C"))),
                Car.of(3L, "KIA", new Price("2000"), Color.BLUE, new Mileage(2000),
                        Set.of(Component.of(2L, "B")))
        );

        var carsSorted = carsService.sortBy(byPrice);
        Assertions
                .assertThat(carsSorted)
                .isEqualTo(carListExpected);
    }
    @Test
    @DisplayName("When there is not descending order")
    void test2() {
        var carListExpected = List.of(
                Car.of(2L, "BMW", new Price("2000"), Color.BLUE, new Mileage(2000),
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C"))),
                Car.of(3L, "KIA", new Price("2000"), Color.BLUE, new Mileage(2000),
                        Set.of(Component.of(2L, "B"))),
                Car.of(1L, "BMW", new Price("20"), Color.WHITE, new Mileage(200),
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A")))
        );

        var carsSorted = carsService.sortBy(byPrice.reversed());
        Assertions
                .assertThat(carsSorted)
                .isEqualTo(carListExpected);
    }

    @Test
    @DisplayName("When there is not enough elements")
    void test3() {
        Assertions
                .assertThat(carsService.sortBy(byPrice))
                .hasSize(3);
    }
}
