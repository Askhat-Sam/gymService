package dev.gymService.dao;

import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class InMemoryStorage {
    private final Map<String, Map<String, List>> inMemoryStorage = new HashMap<>();

    public InMemoryStorage() {
        Map<String, List> trainees = new HashMap<>();
        trainees.put("Trainee1", Arrays.asList("Ivan", "Ivanov", "Ivan.Ivanov", "12345678", true, new Date(12,01,1990), "Furmanova 1", 1));
        trainees.put("Trainee2", Arrays.asList("Sergey", "Sergeyev", "Sergey.Sergeyev", "12345678", true, new Date(11,01,1990), "Furmanova 2", 2));

        inMemoryStorage.put("trainees", trainees);
    }
}
