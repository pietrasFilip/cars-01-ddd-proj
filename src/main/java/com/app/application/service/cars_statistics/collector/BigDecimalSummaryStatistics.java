package com.app.application.service.cars_statistics.collector;

import com.app.application.service.cars_statistics.BigDecimalStatistics;

import java.math.BigDecimal;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class BigDecimalSummaryStatistics implements Collector<BigDecimal, BigDecimalSummaryStatisticsAccumulator, BigDecimalStatistics> {
    @Override
    public Supplier<BigDecimalSummaryStatisticsAccumulator> supplier() {
        return BigDecimalSummaryStatisticsAccumulator::new;
    }

    @Override
    public BiConsumer<BigDecimalSummaryStatisticsAccumulator, BigDecimal> accumulator() {
        return BigDecimalSummaryStatisticsAccumulator::update;
    }

    @Override
    public BinaryOperator<BigDecimalSummaryStatisticsAccumulator> combiner() {
        return (leftAccum, rightAccum) -> {
            leftAccum.update(rightAccum);
            return leftAccum;
        };
    }

    @Override
    public Function<BigDecimalSummaryStatisticsAccumulator, BigDecimalStatistics> finisher() {
        return BigDecimalSummaryStatisticsAccumulator::toBigDecimalStatistics;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.UNORDERED);
    }
}
