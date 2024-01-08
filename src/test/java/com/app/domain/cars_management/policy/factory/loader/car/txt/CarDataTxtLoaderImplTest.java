package com.app.domain.cars_management.policy.factory.loader.car.txt;

import com.app.config.AppTestConfig;
import com.app.infrastructure.persistence.entity.CarEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
class CarDataTxtLoaderImplTest {

    @Autowired
    private CarDataTxtLoader txtLoader;

    @TestFactory
    @DisplayName("When there is data to load from .txt file")
    Stream<DynamicNode> test1() {
        return Stream.of(txtLoader.load())
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Is instance of List",
                                        () -> Assertions.assertThat(n).isInstanceOf(List.class)),
                                dynamicTest("Is instance of CarData",
                                        () -> n.forEach(o -> Assertions.assertThat(o).isInstanceOf(CarEntity.class))),
                                dynamicTest("Has exactly size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }
}
