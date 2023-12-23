package com.app.application.service.cars_statistics.collector;


import com.app.application.service.cars_statistics.BigDecimalStatistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalSummaryStatisticsAccumulator {
    private BigDecimal min;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal sum;
    private int count;

    public void update(BigDecimal value) {
        updateMin(value);
        updateMax(value);
        updateSum(value);
        updateCount(1);
        updateAvg();
    }

    public void update(BigDecimalSummaryStatisticsAccumulator accumulator) {
        updateMin(accumulator.min);
        updateMax(accumulator.max);
        updateSum(accumulator.sum);
        updateCount(accumulator.count);
        updateAvg();
    }

    private void updateMin(BigDecimal value) {
        if (this.min == null) {
            this.min = value;
            return;
        }
        this.min = this.min.compareTo(value) < 0 ? this.min : value;
    }

    private void updateMax(BigDecimal value) {
        if (this.max == null) {
            this.max = value;
            return;
        }
        this.max = this.max.compareTo(value) >= 0 ? this.max : value;
    }

    private void updateSum(BigDecimal value) {
        if (this.sum == null) {
            this.sum = value;
            return;
        }
        this.sum = this.sum.add(value);
    }

    private void updateCount(int value) {
        this.count += value;
    }

    private void updateAvg() {
        if (sum != null && count > 0) {
            this.avg = sum.divide(BigDecimal.valueOf(count), RoundingMode.CEILING);
        }
    }

    public BigDecimalStatistics toBigDecimalStatistics() {
        return new BigDecimalStatistics(min, avg, max, sum, count);
    }
}
