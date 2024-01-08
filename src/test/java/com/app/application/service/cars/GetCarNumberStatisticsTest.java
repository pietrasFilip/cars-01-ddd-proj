package com.app.application.service.cars;

import com.app.application.service.cars_statistics.BigDecimalStatistics;
import com.app.application.service.cars_statistics.NumbersStatisticsType;
import com.app.config.AppTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.app.application.service.cars_statistics.NumbersStatisticsType.MILEAGE;
import static com.app.application.service.cars_statistics.NumbersStatisticsType.PRICE;
import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppTestConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class GetCarNumberStatisticsTest {
    @Autowired
    private CarsService carsService;

    public static Stream<Arguments> argSource() {
        return Stream.of(
                Arguments.of(PRICE, new BigDecimalStatistics(
                        BigDecimal.valueOf(20), BigDecimal.valueOf(1340),
                        BigDecimal.valueOf(2000), BigDecimal.valueOf(4020), 3)),
                Arguments.of(MILEAGE, new BigDecimalStatistics(
                        BigDecimal.valueOf(200), BigDecimal.valueOf(1400), BigDecimal.valueOf(2000),
                        BigDecimal.valueOf(4200), 3))
        );
    }

    @ParameterizedTest
    @MethodSource("argSource")
    @DisplayName("When called for different statistics types")
    void test1(NumbersStatisticsType type, BigDecimalStatistics statisticsExpected) {
        var carsStats = carsService.getCarNumberStatistics(type);

        assertThat(carsStats)
                .isEqualTo(statisticsExpected);
    }
}
