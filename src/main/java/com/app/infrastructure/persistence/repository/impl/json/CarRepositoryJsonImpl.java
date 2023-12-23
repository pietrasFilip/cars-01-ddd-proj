package com.app.infrastructure.persistence.repository.impl.json;

import com.app.domain.cars_management.model.color.Color;
import com.app.domain.cars_management.model.repository.json.CarRepositoryJson;
import com.app.domain.cars_management.model.repository.json.ComponentRepositoryJson;
import com.app.infrastructure.persistence.repository.impl.json.generic.FromJsonToObjectLoader;
import com.app.infrastructure.persistence.entity.CarEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.gson.JsonParser.parseReader;

public class CarRepositoryJsonImpl extends FromJsonToObjectLoader<List<CarEntity>> implements CarRepositoryJson {
    private final ComponentRepositoryJson componentRepository;
    private final String path;
    public CarRepositoryJsonImpl(Gson gson, String path, ComponentRepositoryJson componentRepository) {
        super(gson);
        this.path = path;
        this.componentRepository = componentRepository;
    }


    @Override
    public List<CarEntity> findAll() {
        try (var fileReader = new FileReader(path)){
            var carsObject = parseReader(fileReader).getAsJsonArray();
            var carsList = new ArrayList<JsonObject>();
            carsObject.forEach(c -> carsList.add(c.getAsJsonObject()));
            return carsList
                    .stream()
                    .map(car -> {
                        var components = componentRepository.findAllById(
                                Arrays
                                        .stream(car.getAsJsonArray("components").toString().split(","))
                                        .map(num -> num.replace("[", "").replace("]", ""))
                                        .map(Long::valueOf)
                                        .toList()
                        );
                        return CarEntity
                                .builder()
                                .id(car.get("id").getAsLong())
                                .model(car.get("model").getAsString())
                                .price(car.get("price").getAsString())
                                .color(Color.valueOf(car.get("color").getAsString()))
                                .mileage(car.get("mileage").getAsInt())
                                .components(components)
                                .build();
                    }).toList();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    @Override
    public List<CarEntity> findAllWithoutComponents() {
        return null;
    }
}
