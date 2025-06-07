package dev.gymService.service.interfaces;

import dev.gymService.model.TrainingWorkload;
import dev.gymService.model.dto.TrainingWorkloadRequest;

public interface WorkloadCircuitBreakerService {
    TrainingWorkload getWorkloadSummaryWithCircuitBreaker(String url, TrainingWorkloadRequest trainingWorkloadRequest,
                                                          String transactionId, String jwtToken);
    String updateMicroserviceWithCircuitBreaker(String url, TrainingWorkloadRequest trainingWorkloadRequest, String transactionId, String jwtToken);
}
