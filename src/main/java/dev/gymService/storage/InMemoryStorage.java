package dev.gymService.storage;

import org.springframework.stereotype.Component;

import java.util.*;

public class InMemoryStorage {
    private final Map<String, Map<Long, List>> inMemoryStorage = new HashMap<>();

    public InMemoryStorage() {
        Map<Long, List> trainees = new HashMap<>();
    }

    public Map<String, Map<Long, List>> getInMemoryStorage() {
        return inMemoryStorage;
    }

    public void add(String namespace, Long id, List list) {
        // Add if namespace exist
        if (inMemoryStorage.containsKey(namespace)) {
            inMemoryStorage.get(namespace).put(id, list);
        } else {
            Map<Long, List> map = new HashMap<>();
            map.put(id, list);
            inMemoryStorage.put(namespace, map);
        }
    }

    @Override
    public String toString() {
        return "InMemoryStorage{" +
                "inMemoryStorage=" + inMemoryStorage +
                '}';
    }
}
