package dev.gymService.service.interfaces;

import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;

import java.util.List;

public interface TrainingService {
    Training addTraining(Training training);

    TrainingType getTrainingTypeIdByTrainingName(String trainingName);
    List<TrainingType> getTrainingTypes();
}
