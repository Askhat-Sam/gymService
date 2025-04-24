package dev.gymService.controller;

import dev.gymService.model.Role;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.security.JwtService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.TraineeMapper;
import dev.gymService.utills.TrainerMapper;
import dev.gymService.utills.TrainingMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gym-service/trainers")
public class TrainerController {
    private final TrainerService trainerService;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;
    private final JwtService jwtService;


    public TrainerController(TrainerService trainerService, TraineeMapper traineeMapper, TrainerMapper trainerMapper, TrainingMapper trainingMapper, JwtService jwtService) {
        this.trainerService = trainerService;
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
        this.trainingMapper = trainingMapper;
        this.jwtService = jwtService;
    }

    @PostMapping("/registerNewTrainer")
    @Operation(summary = "Register new trainer",
            description = "Registers new trainer in DB",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Trainer registration request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerRegistrationRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Trainer registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> registerNewTrainer(@RequestBody TrainerRegistrationRequest trainerRegistrationRequest) {
        // Create new trainer
        Trainer trainer = new Trainer();
        trainer.setFirstName(trainerRegistrationRequest.getFirstName());
        trainer.setLastName(trainerRegistrationRequest.getLastName());
        trainer.setSpecialization(trainerRegistrationRequest.getSpecialization());
        trainer.setIsActive(true);
        trainer.setRole(Role.TRAINER);

        // Persist the created trainer into DB
        Trainer createdTrainer = trainerService.createTrainer(trainer);

        var trainerRegistrationResponse = new TrainerRegistrationResponse(createdTrainer.getUserName(), createdTrainer.getPassword());

        // Generate and add token to response
        var jwtToken = jwtService.generateToken(createdTrainer);
        trainerRegistrationResponse.setToken(jwtToken);
        return ResponseEntity.ok().body(trainerRegistrationResponse);
    }

    @PutMapping("/changePassword")
    @Operation(summary = "Change password",
            description = "Changes trainer's password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Password change request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerPasswordChangeRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> changePassword(@RequestBody TrainerPasswordChangeRequest trainerPasswordChangeRequest) {
        // Change user's password
        trainerService.changeTrainerPassword(trainerPasswordChangeRequest.getUserName(),
                trainerPasswordChangeRequest.getNewPassword());

        return ResponseEntity.ok().body("{\"message\": \"Password has been successfully changed\"}");
    }

    @GetMapping("/getTrainerByUsername")
    @Operation(summary = "Get trainer profile",
            description = "Obtains the trainer by username",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Get trainer profile request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerProfileRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public TrainerProfileResponse getTrainerProfile(@RequestBody TrainerProfileRequest trainerProfileRequest) {
        // Get user by userName
        Trainer trainer = trainerService.getTrainerByUserName(trainerProfileRequest.getUserName());

        // Create TraineeProfileResponse
        TrainerProfileResponse trainerProfileResponse = trainerMapper.trainerToTrainerProfileResponseMapper(trainer);

        // Set trainees list into trainerProfileResponse
        trainerProfileResponse.setTrainees(trainer.getTrainees().stream().map(traineeMapper::traineeToTraineeDTOMapper).collect(Collectors.toList()));

        return trainerProfileResponse;
    }

    @PatchMapping("/toggleTrainerStatus")
    @Operation(summary = "Toggle trainer status",
            description = "Toggles trainer status to opposite",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Toggle trainer status request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerStatusToggleRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> toggleTrainerStatus(@RequestBody TrainerStatusToggleRequest trainerStatusToggleRequest) {
        // Change user's status
        trainerService.changeTrainerStatus(trainerStatusToggleRequest.getUserName());

        return ResponseEntity.ok().body("{\"message\": \"User status has been successfully changed\"}");
    }

    @GetMapping("/getTrainerTrainings")
    @Operation(summary = "Get trainer trainings",
            description = "Get trainer trainings",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Get trainer trainings request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerTrainingsRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public List<TrainingDTO> getTraineeTrainings(@RequestBody TrainerTrainingsRequest trainerTrainingsRequest) {
        // Handle optional parameters
        String fromDate = trainerTrainingsRequest.getFromDate() != null ? trainerTrainingsRequest.getFromDate() : "1990-01-01";
        String toDate = trainerTrainingsRequest.getToDate() != null ? trainerTrainingsRequest.getToDate() : "9999-01-01";
        String traineeName = trainerTrainingsRequest.getTraineeName() != null ? trainerTrainingsRequest.getTraineeName() : "";
        Long trainingTypeId = trainerTrainingsRequest.getTrainingType() != null ? trainerTrainingsRequest.getTrainingType() : 0;
        // Get trainings list for user
        List<Training> trainings = trainerService.getTrainerTrainingList(trainerTrainingsRequest.getUserName(), fromDate, toDate
                , traineeName, trainingTypeId);

        return trainings.stream().map(trainingMapper::trainingToTrainingDTOMapper).toList();
    }

    @PutMapping("/updateTrainer")
    @Operation(summary = "Update trainer",
            description = "Updates trainer profile",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update trainer request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TrainerProfileUpdateRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public TrainerProfileUpdateResponse getTrainerProfile(@RequestBody TrainerProfileUpdateRequest trainerProfileUpdateRequest) {
        // Get user by userName
        Trainer trainer = trainerService.getTrainerByUserName(trainerProfileUpdateRequest.getUserName());

        // Update existing trainee
        trainer.setFirstName(trainerProfileUpdateRequest.getFirstName());
        trainer.setLastName(trainerProfileUpdateRequest.getLastName());
        if (trainerProfileUpdateRequest.getSpecialization() != null) {
            trainer.setSpecialization(trainerProfileUpdateRequest.getSpecialization());
        }
        trainer.setIsActive(trainerProfileUpdateRequest.getIsActive());

        // Update user
        Trainer updatedTrainer = trainerService.updateTrainer(trainer, trainerProfileUpdateRequest.getUserName());

        // Prepare response
        TrainerProfileUpdateResponse trainerProfileUpdateResponse = trainerMapper.trainerToTrainerProfileUpdateResponseMapper(updatedTrainer);

        trainerProfileUpdateResponse.setTrainers(updatedTrainer.getTrainees().stream().map(traineeMapper::traineeToTraineeDTOMapper).toList());

        return trainerProfileUpdateResponse;
    }
}
