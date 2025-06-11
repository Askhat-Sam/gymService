package dev.gymService.service.interfaces;

import dev.gymService.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;

public interface WorkloadServiceClient {
    String updateMicroservice(Training training, ActionType actionType, Trainee trainee, Trainer trainer);
    TrainingWorkload getWorkloadSummary(String username);
}
