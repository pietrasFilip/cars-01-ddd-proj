package com.app.infrastructure.persistence.repository.impl.txt;

import com.app.domain.cars_management.model.color.Color;
import com.app.domain.cars_management.model.repository.txt.CarRepositoryTxt;
import com.app.domain.cars_management.model.repository.txt.ComponentRepositoryTxt;
import com.app.infrastructure.persistence.entity.CarEntity;
import lombok.RequiredArgsConstructor;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
public class CarRepositoryTxtImpl implements CarRepositoryTxt {
    private final ComponentRepositoryTxt componentRepository;
    private final String path;

    @Override
    public List<CarEntity> findAll() {
        try (var lines = Files.lines(Paths.get(path))){
            return lines
                    .map(line -> {
                        var items = line.split(";");
                        var components = componentRepository.findAllById(
                                Arrays
                                        .stream(items[5].split(","))
                                        .map(Long::valueOf)
                                        .toList()
                        );
                        return CarEntity
                                .builder()
                                .id(Long.valueOf(items[0]))
                                .model(items[1])
                                .price(items[2])
                                .color(Color.valueOf(items[3]))
                                .mileage(parseInt(items[4]))
                                .components(components)
                                .build();
                    }).toList();
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
