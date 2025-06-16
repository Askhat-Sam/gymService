package dev.gymService.service.implementations;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.gymService.model.*;
import dev.gymService.model.dto.TrainingWorkloadRequest;
import dev.gymService.service.interfaces.WorkloadCircuitBreakerService;
import dev.gymService.service.interfaces.WorkloadServiceClient;
import dev.gymService.utills.TrainingMapper;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
@Profile("rest")
@Service
public class RestWorkloadServiceClient implements WorkloadServiceClient {
    private final TrainingMapper trainingMapper;
    private final EurekaClient eurekaClient;
    private final WorkloadCircuitBreakerService workloadCircuitBreakerService;

    public RestWorkloadServiceClient(TrainingMapper trainingMapper, EurekaClient eurekaClient, WorkloadCircuitBreakerServiceImpl workloadCircuitBreakerService) {
        this.trainingMapper = trainingMapper;
        this.eurekaClient = eurekaClient;
        this.workloadCircuitBreakerService = workloadCircuitBreakerService;
    }

    @Override
    public String updateMicroservice(Training training, ActionType actionType, Trainee trainee, Trainer trainer) {
        // Create TrainingWorkloadRequest object
        TrainingWorkloadRequest trainingWorkloadRequest = trainingMapper.trainingToWorkloadRequest(training);
        trainingWorkloadRequest.setTrainerUsername(trainer.getUsername());
        trainingWorkloadRequest.setActionType(actionType);
        trainingWorkloadRequest.setTrainerFirstName(trainer.getFirstName());
        trainingWorkloadRequest.setTrainerLastName(trainer.getLastName());
        trainingWorkloadRequest.setActive(trainer.getIsActive());

        // Get the service by service name from Eureka discovery service
        InstanceInfo service = getService("workload");

        // Get hostName and port from Eureka discovery service
        String hostName = service.getHostName();
        int port = service.getPort();

        // Prepare url for "Workload" microservice
        String url = "http://" + hostName + ":" + port + "/workload-service/updateWorkload";

        // Get current transaction id from httpServletRequest
        String transactionId = MDC.get("transactionId");

        // Extract the original JWT token from the SecurityContextHolder
        String jwtToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        // Make call to "Workload" microservice using circuit breaker
       return workloadCircuitBreakerService.updateMicroserviceWithCircuitBreaker(url, trainingWorkloadRequest, transactionId, jwtToken);
    }

    @Override
    public TrainingWorkload getWorkloadSummary(@RequestParam String username) {
        // Create TrainingWorkloadRequest object
        TrainingWorkloadRequest trainingWorkloadRequest = new TrainingWorkloadRequest();
        trainingWorkloadRequest.setTrainerUsername(username);

        // Get the service by service name from Eureka discovery service
        InstanceInfo service = getService("workload");

        // Get hostName and port from Eureka discovery service
        String hostName = service.getHostName();
        int port = service.getPort();

            // Prepare url for "Workload" microservice
        String  url = "http://" + hostName + ":" + port + "/workload-service/getWorkloadSummary?username=" + URLEncoder.encode(username, StandardCharsets.UTF_8);

        // Get current transaction id from httpServletRequest
        String transactionId = MDC.get("transactionId");

        // Extract the original JWT token from the SecurityContextHolder
        String jwtToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        // Make call to "Workload" microservice using circuit breaker
        return workloadCircuitBreakerService.getWorkloadSummaryWithCircuitBreaker(url, trainingWorkloadRequest, transactionId, jwtToken);
    }

    private InstanceInfo getService(String serviceName) {
        return  eurekaClient
                .getApplication(serviceName)
                .getInstances()
                .get(0);
    }
}
