package dev.gymService.service.implementations;

import dev.gymService.model.TrainingWorkload;
import dev.gymService.model.dto.TrainingWorkloadRequest;
import dev.gymService.service.interfaces.WorkloadCircuitBreakerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WorkloadCircuitBreakerServiceImpl implements WorkloadCircuitBreakerService {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(WorkloadCircuitBreakerServiceImpl.class);

    public WorkloadCircuitBreakerServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "workloadService", fallbackMethod = "getWorkloadSummaryFallback")
    public TrainingWorkload getWorkloadSummaryWithCircuitBreaker(String url, TrainingWorkloadRequest trainingWorkloadRequest,
                                                                 String transactionId, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (transactionId != null) {
            headers.set("transactionId", transactionId);
        }

        if (jwtToken != null && !jwtToken.isBlank()) {
            headers.set("Authorization", jwtToken);
        }

        HttpEntity<TrainingWorkloadRequest> httpEntity = new HttpEntity<>(trainingWorkloadRequest, headers);
        return restTemplate.postForObject(url, httpEntity, TrainingWorkload.class);
    }

    public TrainingWorkload getWorkloadSummaryFallback(String url, TrainingWorkloadRequest trainingWorkloadRequest,
                                                       String transactionId, String jwtToken, Throwable throwable) {
        logger.info("Fallback: Workload service is unavailable. Failed to call getWorkloadSummary method");
        throw new RuntimeException("{\"error\": \"Fallback: Workload service is unavailable\"}");
    }


    @CircuitBreaker(name = "workloadService", fallbackMethod = "updateMicroserviceFallback")
    public String updateMicroserviceWithCircuitBreaker(String url, TrainingWorkloadRequest trainingWorkloadRequest, String transactionId, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (transactionId != null) {
            headers.set("transactionId", transactionId);
        }

        if (jwtToken != null && !jwtToken.isBlank()) {
            headers.set("Authorization", jwtToken);
        }

        HttpEntity<TrainingWorkloadRequest> httpEntity = new HttpEntity<>(trainingWorkloadRequest, headers);
        return restTemplate.postForObject(url, httpEntity, String.class);
    }

    public String  updateMicroserviceFallback(String url, TrainingWorkloadRequest trainingWorkloadRequest,
                                                       String transactionId, String jwtToken, Throwable throwable) {
        logger.info("Fallback: Workload service is unavailable. Failed to call updateMicroservice method");
        throw new RuntimeException("{\"error\": \"Fallback: Workload service is unavailable\"}");
    }
}