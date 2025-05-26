package dev.gymService.controller;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.model.dto.TrainingAddRequest;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gym-service/trainings")
public class TrainingController {
    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    public TrainingController(TrainingService trainingService, TraineeService traineeService, TrainerService trainerService) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
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
    public ResponseEntity<?> addTraining(@RequestBody TrainingAddRequest trainingAddRequest) {
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
}
