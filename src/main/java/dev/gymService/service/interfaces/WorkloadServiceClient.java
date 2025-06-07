package dev.gymService.service.interfaces;

import dev.gymService.model.*;
import jakarta.servlet.http.HttpServletRequest;

public interface WorkloadServiceClient {
    String updateMicroservice(Training training, ActionType actionType, Trainee trainee, Trainer trainer, HttpServletRequest httpServletRequest);
    TrainingWorkload getWorkloadSummary(String username, HttpServletRequest httpServletRequest);
}
