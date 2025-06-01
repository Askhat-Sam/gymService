package dev.gymService.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.model.dto.TrainingAddRequest;
import dev.gymService.model.dto.TrainingWorkloadRequest;
import dev.gymService.model.dto.TrainingWorkloadRequestAbstract;
import dev.gymService.model.dto.TrainingWorkloadTotalRequest;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.TrainingMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/gym-service/trainings")
public class TrainingController {
    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final EurekaClient eurekaClient;
    private final RestTemplate restTemplate;
    private final TrainingMapper trainingMapper;

    public TrainingController(TrainingService trainingService, TraineeService traineeService, TrainerService trainerService, EurekaClient eurekaClient, RestTemplate restTemplate, TrainingMapper trainingMapper) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.eurekaClient = eurekaClient;
        this.restTemplate = restTemplate;
        this.trainingMapper = trainingMapper;
    }

    @PostMapping("/addTraining")
    @Operation(summary = "Add new training",
            description = "Creates new training",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Add new training request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainingAddRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Training added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> addTraining(@RequestBody TrainingAddRequest trainingAddRequest, HttpServletRequest httpServletRequest) {
        // Get trainee/trainer by userName
        Trainee trainee = traineeService.getTraineeByUserName(trainingAddRequest.getTraineeUserName());
        Trainer trainer = trainerService.getTrainerByUserName(trainingAddRequest.getTrainerUsername());
        TrainingType trainingType = trainingService.getTrainingTypeIdByTrainingName(trainingAddRequest.getTrainingName());

        // Create training object
        Training training = new Training();
        training.setTraineeId(trainee.getId());
        training.setTrainerId(trainer.getId());
        training.setTrainingName(trainingAddRequest.getTrainingName());
        training.setTrainingTypeId(trainingType.getId());
        training.setTrainingDate(trainingAddRequest.getTrainingDate());
        training.setTrainingDuration(trainingAddRequest.getTrainingDuration());
        training.setTrainee(trainee);
        training.setTrainer(trainer);

        training = trainingService.addTraining(training);

        // Create TrainingWorkloadRequest object
        TrainingWorkloadRequest trainingWorkloadRequest = trainingMapper.trainingAddRequestToWorkloadRequest(trainingAddRequest);
        trainingWorkloadRequest.setActionType(trainingAddRequest.getActionType());
        trainingWorkloadRequest.setTrainerFirstName(trainer.getFirstName());
        trainingWorkloadRequest.setTrainerLastName(trainer.getLastName());
        trainingWorkloadRequest.setActive(trainer.getIsActive());

        // Get the service by service name from Eureka discovery service
        InstanceInfo service = eurekaClient
                .getApplication("workload")
                .getInstances()
                .get(0);

        // Get hostName and port from Eureka discovery service
        String hostName = service.getHostName();
        int port = service.getPort();

        // Prepare url for "Workload" microservice
        String url = "http://" + hostName + ":" + port + "/workload-service/updateWorkload";

        // Get current transaction id from httpServletRequest
        String transactionId = MDC.get("transactionId");

        // Extract the original JWT token from the Authorization header
        String jwtToken = httpServletRequest.getHeader("Authorization");

        // Make call to "Workload" microservice using circuit breaker
        String response = callWorkloadServiceWithCircuitBreaker(url, trainingWorkloadRequest, transactionId, jwtToken);

        // Add body to response entity
        ResponseEntity<String> responseEntity;
        if (training != null) {
            responseEntity = ResponseEntity.ok().body("{\"message\": \"Training has been added successfully\"}");
        } else {
            responseEntity = ResponseEntity.badRequest().body("{\"error\": \"Failure to add training\"}");
        }

        return responseEntity;
    }

    @CircuitBreaker(name = "workloadService", fallbackMethod = "fallbackForWorkloadService")
    private String callWorkloadServiceWithCircuitBreaker(String url, TrainingWorkloadRequestAbstract trainingWorkloadRequest,
                                                         String transactionId, String jwtToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        if (transactionId != null) {
            headers.set("transactionId", transactionId);
        }

        if (jwtToken != null && !jwtToken.isBlank()) {
            headers.set("Authorization", jwtToken);
        }

        HttpEntity<TrainingWorkloadRequestAbstract> httpEntity = new HttpEntity<>(trainingWorkloadRequest, headers);
        return restTemplate.postForObject(url, httpEntity, String.class);
    }

    private String fallbackForWorkloadService(String url, TrainingWorkloadRequestAbstract trainingWorkloadRequest,
                                              String transactionId, String jwtToken, Throwable throwable) {
        return "{\"error\": \"Fallback: Workload service is unavailable\"}";
    }



    @GetMapping("/getTrainingTypes")
    @Operation(
            summary = "Get training types",
            description = "Obtains training types"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public List<TrainingType> getTrainingTypes() {
        return trainingService.getTrainingTypes();
    }

    @PostMapping("/getMonthlyWorkload")
    public String getMonthlyWorkload(@RequestBody TrainingWorkloadTotalRequest trainingWorkloadTotalRequest,
                                   HttpServletRequest httpServletRequest) {
        // Get the service by service name from Eureka discovery service
        InstanceInfo service = eurekaClient
                .getApplication("workload")
                .getInstances()
                .get(0);

        // Get hostName and port from Eureka discovery service
        String hostName = service.getHostName();
        int port = service.getPort();

        // Prepare url for "Workload" microservice
        String url = "http://" + hostName + ":" + port + "/workload-service/getMonthlyWorkload";

        // Get current transaction id from httpServletRequest
        String transactionId = MDC.get("transactionId");

        // Extract the original JWT token from the Authorization header
        String jwtToken = httpServletRequest.getHeader("Authorization");

        // Make call to "Workload" microservice using circuit breaker
        return callWorkloadServiceWithCircuitBreaker(url, trainingWorkloadTotalRequest, transactionId, jwtToken);
    }
}
