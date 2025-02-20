package dev.gymService.service;

import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;

import java.util.Date;

public interface TrainingService {
    Training createTraining(Long traineeId, Long trainerId, String trainingName, TrainingType trainingType,
                            Date trainingDate, Long trainingDuration);
}
