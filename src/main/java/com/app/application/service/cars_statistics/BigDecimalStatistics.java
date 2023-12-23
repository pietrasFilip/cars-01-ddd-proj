package com.app.application.service.cars_statistics;

import java.math.BigDecimal;

public record BigDecimalStatistics(
        BigDecimal min,
        BigDecimal avg,
        BigDecimal max,
        BigDecimal sum,
        int count
) {
    public BigDecimalStatistics() {
        this(null, null, null, null, 0);
    }
}
