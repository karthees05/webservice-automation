package com.example.api.utils;

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

}
