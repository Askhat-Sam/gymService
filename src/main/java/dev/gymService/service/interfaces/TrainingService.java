package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TrainingService {
    void createTraining(Training training);

    void updateTraining(Training training);

    void deleteTraining(Long id);

    Training getTrainingById(Long id);

    List<Training> getAllTrainings();
}
