package com.app.domain.cars_management.model;

import com.app.domain.cars_management.model.color.Color;
import com.app.domain.cars_management.model.vo.Mileage;
import com.app.domain.cars_management.model.vo.Price;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Car {
    final Long id;
    final String model;
    final Price price;
    final Color color;
    final Mileage mileage;
    final Set<Component> components;
    private static final Logger logger = LogManager.getRootLogger();

    // Methods that give information about car

    /**
     *
     * @param component Component to check.
     * @return True when car contains given component or false when not.
     */
    public boolean hasComponent(Component component) {
        if (component == null) {
            logger.error("Given component is null");
            throw new IllegalStateException("Component is null");
        }

        if (components == null) {
            logger.error("Car components are null");
            throw new IllegalStateException("Car without components");
        }

        return components.contains(component);
    }

    /**
     *
     * @param referenceMileage Mileage to check.
     * @return True when given mileage is smaller than car mileage or false when not.
     */
    public boolean hasMileageGreaterThan(int referenceMileage) {
        return this.mileage.hasMileageGreaterThan(referenceMileage);
    }

    /**
     *
     * @param from Price lower limit.
     * @param to Price upper limit.
     * @return True when car price is between given limits or false when not.
     */
    public boolean hasPriceBetween(BigDecimal from, BigDecimal to) {
        return this.price.hasPriceBetween(from, to);
    }

    /**
     *
     * @param componentsComparator Comparator for components.
     * @return Car with components sorted by comparator.
     */
    public Car withSortedComponents(Comparator<Component> componentsComparator) {
        if (components == null || components.isEmpty()) {
            logger.error("Car components are null or empty");
            throw new IllegalStateException("Car without components");
        }

        var sortedComponents = components.stream().sorted(componentsComparator)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return Car
                .builder()
                .id(id)
                .model(model)
                .price(price)
                .color(color)
                .mileage(mileage)
                .components(sortedComponents)
                .build();
    }

    // Methods from Object class

    @Override
    public String toString() {
        return "CAR=[MODEL= %s, PRICE= %s, COLOR= %s, MILEAGE= %s, COMPONENTS= %s]%n"
                .formatted(model, price.toString(), color, mileage.toString(), components);
    }

    // Static methods

    public static Car of(Long id, String model, Price price, Color color, Mileage mileage, Set<Component> components) {
        return new Car(id, model, price, color, mileage, components);
    }
}
