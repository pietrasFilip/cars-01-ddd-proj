package com.app.domain.cars_management.model.vo;

import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EqualsAndHashCode
public class Mileage {
    final int value;
    private static final Logger logger = LogManager.getRootLogger();

    public Mileage(int value) {
        this.value = init(value);
    }

    /**
     *
     * @param referenceMileage Mileage to check.
     * @return True when given mileage is smaller than car mileage or false when not.
     */
    public boolean hasMileageGreaterThan(int referenceMileage) {
        if (referenceMileage < 0) {
            logger.error("Reference mileage is less than zero");
            throw new IllegalStateException("Mileage less than zero");
        }
        return this.value > referenceMileage;
    }

    @Override
    public String toString() {
        return "Mileage: %s".formatted(value);
    }

    private int init(int value) {
        if (value < 0) {
            throw new IllegalStateException("Mileage is not correct");
        }
        return value;
    }
}
