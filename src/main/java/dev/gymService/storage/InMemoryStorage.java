package dev.gymService.storage;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class InMemoryStorage {
    private final Map<Long, Trainee> traineeMap = new HashMap<>();
    private final Map<Long, Trainer> trainerMap = new HashMap<>();
    private final Map<Long, Training> trainingMap = new HashMap<>();

    public Map<Long, Trainee> getTraineeStorage() {
        return traineeMap;
    }

    public Map<Long, Trainer> getTrainerStorage() {
        return trainerMap;
    }

    public Map<Long, Training> getTrainingStorage() {
        return trainingMap;
    }
}
