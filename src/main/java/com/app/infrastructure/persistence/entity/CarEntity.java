package com.app.infrastructure.persistence.entity;

import com.app.domain.cars_management.model.Car;
import com.app.domain.cars_management.model.Component;
import com.app.domain.cars_management.model.color.Color;
import com.app.domain.cars_management.model.vo.Mileage;
import com.app.domain.cars_management.model.vo.Price;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static jakarta.persistence.CascadeType.MERGE;
import static jakarta.persistence.CascadeType.PERSIST;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class CarEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String model;
    private String price;
    private Color color;
    private int mileage;
    @ManyToMany(
            cascade = {MERGE, PERSIST},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "cars_components",
            joinColumns = {@JoinColumn(name = "car_id")},
            inverseJoinColumns = {@JoinColumn(name = "component_id")}
    )
    @Builder.Default
    private List<ComponentEntity> components = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarEntity that = (CarEntity) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public Car toDomain() {
        return Car
                .builder()
                .id(id)
                .model(model)
                .price(new Price(price))
                .color(color)
                .mileage(new Mileage(mileage))
                .components(toComponents())
                .build();
    }

    private Set<Component> toComponents() {
        return components != null ? components
                .stream()
                .map(ComponentEntity::toDomain)
                .collect(Collectors.toSet()) : null;
    }
}
