package com.app.infrastructure.api.transformer;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonTransformerImpl implements JsonTransformer {
    private final Gson gson;
    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}
