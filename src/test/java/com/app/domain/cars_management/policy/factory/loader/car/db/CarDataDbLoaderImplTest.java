package com.app.domain.cars_management.policy.factory.loader.car.db;

import com.app.infrastructure.persistence.entity.CarEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static com.app.domain.cars_management.model.color.Color.BLUE;
import static com.app.domain.cars_management.model.color.Color.WHITE;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarDataDbLoaderImplTest {
    @Mock
    CarDataDbLoaderImpl carDataDbLoader;

    @TestFactory
    @DisplayName("When there is data to load from db")
    Stream<DynamicNode> test1() {
        when(carDataDbLoader.load())
                .thenAnswer(invocationOnMock -> List.of(
                        CarEntity
                                .builder()
                                .id(1L)
                                .model("BMW")
                                .price("20")
                                .color(WHITE)
                                .mileage(200)
                                .components(null)
                                .build(),
                        CarEntity
                                .builder()
                                .id(2L)
                                .model("BMW")
                                .price("2000")
                                .color(BLUE)
                                .mileage(2000)
                                .components(null)
                                .build(),
                        CarEntity
                                .builder()
                                .id(3L)
                                .model("KIA")
                                .price("2000")
                                .color(BLUE)
                                .mileage(2000)
                                .components(null)
                                .build()
                ));

        return Stream.of(carDataDbLoader.load())
                .map(n -> dynamicContainer(
                        "Container" + n, Stream.of(
                                dynamicTest("Components are null",
                                        () -> n.forEach(car -> Assertions.assertThat(car.getComponents()).isNull())),
                                dynamicTest("Is instance of List",
                                        () -> Assertions.assertThat(n).isInstanceOf(List.class)),
                                dynamicTest("Is instance of CarData",
                                        () -> n.forEach(car -> Assertions.assertThat(car).isInstanceOf(CarEntity.class))),
                                dynamicTest("Has Exactly size of 3",
                                        () -> Assertions.assertThat(n).hasSize(3))
                        )
                ));
    }
}
