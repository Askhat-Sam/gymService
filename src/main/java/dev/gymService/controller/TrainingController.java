package dev.gymService.controller;

import com.netflix.discovery.EurekaClient;
import dev.gymService.model.*;
import dev.gymService.model.dto.TrainingAddRequest;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.TrainingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
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

        // Get action type
        ActionType actionType = trainingAddRequest.getActionType();

        training = trainingService.addTraining(training, actionType, trainee, trainer, httpServletRequest);
        // Add body to response entity
        ResponseEntity<String> responseEntity;
        if (training != null) {
            responseEntity = ResponseEntity.ok().body("{\"message\": \"Training has been added successfully\"}");
            ;
        } else {
            responseEntity = ResponseEntity.badRequest().body("{\"error\": \"Failure to add training\"}");
        }

        return responseEntity;
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

    @PostMapping("/getWorkloadSummary")
    public TrainingWorkload getMonthlyWorkload(@RequestParam String username, HttpServletRequest httpServletRequest) {
        return trainingService.getWorkloadSummary(username, httpServletRequest);
    }
}
