package dev.gymService.service.interfaces;

import dev.gymService.model.*;

public interface WorkloadServiceClient {
    String updateMicroservice(Training training, ActionType actionType, Trainee trainee, Trainer trainer);
    TrainingWorkload getWorkloadSummary(String username);
}
