package dev.gymService.service.interfaces;

import dev.gymService.model.Training;

import java.util.List;

public interface TrainingService {
    Training createTraining(Training training);

    Training getTrainingById(Long id);

    List<Training> getAllTrainings();
}
