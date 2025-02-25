package dev.gymService.dao;

import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class InMemoryStorage {
    private final Map<String, Map<Long, List>> inMemoryStorage = new HashMap<>();

    public InMemoryStorage() {
        Map<Long, List> trainees = new HashMap<>();
        trainees.put(1L, Arrays.asList("Ivan", "Ivanov", "Ivan.Ivanov", "12345678", true, new Date(12,01,1990), "Furmanova 1", 1));
        trainees.put(2L, Arrays.asList("Sergey", "Sergeyev", "Sergey.Sergeyev", "12345678", true, new Date(11,01,1990), "Akhmetova 4a", 2));
        trainees.put(3L, Arrays.asList("Pavel", "Pavlov", "Pavel.Pavlov", "12345678", true, new Date(10,05,1991), "Omarova 19", 3));

        inMemoryStorage.put("trainees", trainees);
    }

    public Map<String, Map<Long, List>> getInMemoryStorage(){
        return inMemoryStorage;
    }
}
