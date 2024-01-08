package com.app.domain.cars_management.policy.factory.converter;

import com.app.config.AppTestConfig;
import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.policy.factory.loader.car.txt.CarDataTxtLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
class ToCarConverterImplTest {
    @Autowired
    private CarDataTxtLoader txtLoader;

    @TestFactory
    @DisplayName("When there is conversion from CarEntity to Car")
    Stream<DynamicNode> test1() {
        var data = txtLoader.load();

        var converter = new ToCarConverterImpl();
        return Stream.of(converter.convert(data))
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of Car",
                                        () -> n.forEach(o -> Assertions.assertThat(o).isInstanceOf(Car.class))),
                                dynamicTest("Has size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }
}
