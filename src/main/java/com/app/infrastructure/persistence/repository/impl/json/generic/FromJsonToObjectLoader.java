package com.app.infrastructure.persistence.repository.impl.json.generic;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Component
public abstract class FromJsonToObjectLoader<T> {
    final Gson gson;
    final Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected FromJsonToObjectLoader(Gson gson) {
        this.gson = gson;
    }

    public T loadObject(String path) {
        try (var fileReader = new FileReader(path)){
            return gson.fromJson(fileReader, type);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
