package dev.gymService.service.interfaces;

import dev.gymService.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

public interface TrainingService {
    Training addTraining(Training training, ActionType actionType, Trainee trainee, Trainer trainer);
    TrainingType getTrainingTypeIdByTrainingName(String trainingName);
    List<TrainingType> getTrainingTypes();
    TrainingWorkload getWorkloadSummary(String username);
}
