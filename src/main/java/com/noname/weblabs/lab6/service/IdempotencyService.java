package com.noname.weblabs.lab6.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

@Service
public class IdempotencyService {
    private final Map<String, Object> store = new ConcurrentHashMap<>();

    public <T> T get(String key) {
        return (T) store.get(key);
    }

    public <T> T saveIfNotExists(String key, Supplier<T> initializer) {
        return (T) store.computeIfAbsent(key, key2 -> initializer.get());
    }
}