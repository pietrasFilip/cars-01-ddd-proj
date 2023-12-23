package com.app.domain.cars_management.model.vo;

import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode
public class Price {
    final BigDecimal value;

    public Price(String value) {
        this.value = init(value);
    }

    /**
     *
     * @param from Price lower limit.
     * @param to Price upper limit.
     * @return True when car price is between given limits or false when not.
     */
    public boolean hasPriceBetween(BigDecimal from, BigDecimal to) {
        return this.value.compareTo(from) >= 0 && this.value.compareTo(to) <= 0;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    private BigDecimal init(String valueStr) {
        if (valueStr == null || !valueStr.matches("\\d+(\\.\\d{2})?")) {
            throw new IllegalStateException("Price is not correct");
        }
        return new BigDecimal(valueStr);
    }
}
