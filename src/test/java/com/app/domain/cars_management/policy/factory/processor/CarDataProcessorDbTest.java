package com.app.domain.cars_management.policy.factory.processor;

import com.app.config.AppTestConfig;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import com.app.domain.cars_management.model.vo.Mileage;
import com.app.domain.cars_management.model.vo.Price;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Set;

import static com.app.domain.cars_management.model.color.Color.BLUE;
import static com.app.domain.cars_management.model.color.Color.WHITE;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:app-test.properties")
class CarDataProcessorDbTest {
    @Autowired
    @Qualifier("dbProcessor") CarDataProcessor carDataProcessorDb;

    @Test
    @DisplayName("When data source is db")
    void test1() {
        var expected = List.of(
                Car.of(
                        1L, "BMW", new Price("20"), WHITE, new Mileage(200),
                        Set.of(Component.of(2L, "B"), Component.of(1L, "A"))
                ),
                Car.of(
                        2L, "BMW", new Price("2000"), BLUE, new Mileage(2000),
                        Set.of(Component.of(1L, "A"), Component.of(3L, "C"))
                ),
                Car.of(
                        3L, "KIA", new Price("2000"), BLUE, new Mileage(2000),
                        Set.of(Component.of(2L, "B"))
                )
        );

        Assertions
                .assertThat(carDataProcessorDb.process())
                .hasSize(3)
                .isInstanceOf(List.class)
                .isEqualTo(expected);
    }
}
