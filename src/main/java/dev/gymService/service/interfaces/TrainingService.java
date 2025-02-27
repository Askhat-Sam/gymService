package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TrainingService {
    Training createTraining(Training training);

    Training updateTraining(Training training);

    void deleteTraining(Long id);

    Training getTrainingById(Long id);

    List<Training> getAllTrainings();
    Long generateTrainingId();
}
