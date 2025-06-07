package dev.gymService.service.interfaces;

import dev.gymService.model.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface TrainingService {
    Training addTraining(Training training, ActionType actionType, Trainee trainee, Trainer trainer, HttpServletRequest httpServletRequest);

    TrainingType getTrainingTypeIdByTrainingName(String trainingName);
    List<TrainingType> getTrainingTypes();
    TrainingWorkload getWorkloadSummary(String username, HttpServletRequest httpServletRequest);
}
