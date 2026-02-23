package com.example.api.utils;

import io.cucumber.java.Scenario;
import java.util.HashMap;
import java.util.Map;

public class TestContext {
    private final Map<String, Object> context = new HashMap<>();

    public void set(String key, Object value) {
        context.put(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> clazz) {
        return (T) context.get(key);
    }
}
