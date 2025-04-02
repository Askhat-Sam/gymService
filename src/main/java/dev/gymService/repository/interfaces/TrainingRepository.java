package dev.gymService.repository.interfaces;

import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;

import java.util.List;

public interface TrainingRepository {
    Training addTraining(Training training);

    TrainingType getTrainingTypeIdByTrainingName(String trainingName);

    List<TrainingType> getTrainingTypes();
}
