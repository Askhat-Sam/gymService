package dev.gymService.controller;

import dev.gymService.model.Role;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.security.JwtService;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.TraineeMapper;
import dev.gymService.utills.TrainerMapper;
import dev.gymService.utills.TrainingMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gym-service/trainees")
public class TraineeController {
    private final TraineeService traineeService;
    private final TraineeMapper traineeMapper;
    private final TrainerMapper trainerMapper;
    private final TrainingMapper trainingMapper;
    private final JwtService jwtService;
    private final AuthenticationController authenticationController;

    public TraineeController(TraineeService traineeService, TraineeMapper traineeMapper, TrainerMapper trainerMapper, TrainingMapper trainingMapper, JwtService jwtService, @Lazy AuthenticationController authenticationController) {

    private final Counter loginCallCounter;

    public TraineeController(TraineeService traineeService, TraineeMapper traineeMapper, TrainerMapper trainerMapper, TrainingMapper trainingMapper, MeterRegistry meterRegistry) {
        this.traineeService = traineeService;
        this.traineeMapper = traineeMapper;
        this.trainerMapper = trainerMapper;
        this.trainingMapper = trainingMapper;
        this.jwtService = jwtService;
        this.authenticationController = authenticationController;
        this.loginCallCounter = Counter.builder("login_call_counter")
                .description("Number of logins")
                .register(meterRegistry);
    }

    @PostMapping("/registerNewTrainee")
    @Operation(summary = "Register new trainee",
            description = "Registers new trainee in DB",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Trainee registration request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeRegistrationRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Trainee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> registerNewTrainee(@RequestBody TraineeRegistrationRequest traineeRegistrationRequest) {
        // Create new trainee
        Trainee trainee = traineeMapper.traineeRegistrationRequestToTrainee(traineeRegistrationRequest);
        trainee.setIsActive(true);
        trainee.setRole(Role.TRAINEE);

        // Persist the created trainee into DB
        Trainee createdTrainee = traineeService.createTrainee(trainee);

        var traineeRegistrationResponse = new TraineeRegistrationResponse(createdTrainee.getUserName(), createdTrainee.getPassword());

        // Generate and add token to response
        var jwtToken = jwtService.generateToken(trainee);
        traineeRegistrationResponse.setToken(jwtToken);

        return ResponseEntity.ok().body(traineeRegistrationResponse);
    }

    @PutMapping("/changePassword")
    @Operation(summary = "Change password",
            description = "Changes trainee's password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Password change request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineePasswordChangeRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> changePassword(@RequestBody TraineePasswordChangeRequest traineePasswordChangeRequest) {
        // Change user's password
        traineeService.changeTraineePassword(traineePasswordChangeRequest.getUserName(),
                traineePasswordChangeRequest.getNewPassword());

        return ResponseEntity.ok().body("{\"message\": \"Password has been successfully changed\"}");
    }

    @GetMapping("/getTraineeProfile")
    @Operation(summary = "Get trainee profile",
            description = "Obtains the trainee by username",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Get trainee profile request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeProfileRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public TraineeProfileResponse getTraineeProfile(@RequestBody TraineeProfileRequest traineeProfileRequest) {
        // Get user by userName
        Trainee trainee = traineeService.getTraineeByUserName(traineeProfileRequest.getUserName());

        // Create TraineeProfileResponse
        TraineeProfileResponse traineeProfileResponse = traineeMapper.traineeToTraineeProfileResponse(trainee);

        // Set trainers list into trainee
        traineeProfileResponse.setTrainers(trainee.getTrainers().stream().map(trainerMapper::trainerToTrainerDTOMapper).collect(Collectors.toList()));

        return traineeProfileResponse;
    }

    @PatchMapping("/toggleTraineeStatus")
    @Operation(summary = "Toggle trainee status",
            description = "Toggles trainee status to opposite",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Toggle trainee status request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeStatusToggleRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> toggleTraineeStatus(@RequestBody TraineeStatusToggleRequest traineeStatusToggleRequest) {
        // Change user's status
        traineeService.changeTraineeStatus(traineeStatusToggleRequest.getUserName());

        return ResponseEntity.ok().body("{\"message\": \"User status has been successfully changed\"}");
    }

    @DeleteMapping("/deleteTrainee")
    @Operation(summary = "Delete trainee",
            description = "Delete trainee",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Delete trainee request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeDeleteRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<?> deleteTrainee(@RequestBody TraineeDeleteRequest traineeDeleteRequest) {
        // Delete trainee
        traineeService.deleteTraineeByUserName(traineeDeleteRequest.getUserName());

        return ResponseEntity.ok().body("{\"message\": \"User has been successfully deleted\"}");
    }

    @GetMapping("/getTraineeTrainings")
    @Operation(summary = "Get trainee trainings",
            description = "Get trainee trainings",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Get trainee trainings request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeTrainingsRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public List<TrainingDTO> getTraineeTrainings(@RequestBody TraineeTrainingsRequest traineeTrainingsRequest) {
        // Handle optional parameters
        String fromDate = traineeTrainingsRequest.getFromDate() != null ? traineeTrainingsRequest.getFromDate() : "1990-01-01";
        String toDate = traineeTrainingsRequest.getToDate() != null ? traineeTrainingsRequest.getToDate() : "9999-01-01";
        String trainerName = traineeTrainingsRequest.getTrainerName() != null ? traineeTrainingsRequest.getTrainerName() : "";
        Long trainingTypeId = traineeTrainingsRequest.getTrainingType() != null ? traineeTrainingsRequest.getTrainingType() : 0;

        // Get trainings list for user
        List<Training> trainings = traineeService.getTraineeTrainingList(traineeTrainingsRequest.getUserName(), fromDate, toDate, trainerName, trainingTypeId);

        return trainings.stream().map(trainingMapper::trainingToTrainingDTOMapper).toList();
    }

    @PutMapping("/updateTrainee")
    @Operation(summary = "Update trainee",
            description = "Updates trainee profile",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update trainee request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeProfileUpdateRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public TraineeProfileUpdateResponse updateTrainee(@RequestBody TraineeProfileUpdateRequest traineeProfileUpdateRequest) {
        // Get user by userName
        Trainee trainee = traineeService.getTraineeByUserName(traineeProfileUpdateRequest.getUserName());

        // Update existing trainee
        trainee.setFirstName(traineeProfileUpdateRequest.getFirstName());
        trainee.setLastName(traineeProfileUpdateRequest.getLastName());
        if (traineeProfileUpdateRequest.getDateOfBirth() != null) {
            trainee.setDateOfBirth(traineeProfileUpdateRequest.getDateOfBirth());
        }
        if (traineeProfileUpdateRequest.getAddress() != null) {
            trainee.setAddress(traineeProfileUpdateRequest.getAddress());
        }
        trainee.setIsActive(traineeProfileUpdateRequest.getIsActive());

        // Update user
        Trainee updatedTrainee = traineeService.updateTrainee(trainee, traineeProfileUpdateRequest.getUserName());

        // Prepare response
        TraineeProfileUpdateResponse traineeProfileUpdateResponse = traineeMapper.traineeToTraineeProfileUpdateResponse(updatedTrainee);

        traineeProfileUpdateResponse.setTrainers(updatedTrainee.getTrainers().stream().map(trainerMapper::trainerToTrainerDTOMapper).toList());

        return traineeProfileUpdateResponse;
    }


    @PutMapping("/updateTraineeTrainers")
    @Operation(summary = "Update trainee trainers",
            description = "Updates trainee trainers list",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Update trainee trainers request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeTrainersListUpdateRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public TraineeTrainersListUpdateResponse updateTraineeTrainers(@RequestBody TraineeTrainersListUpdateRequest traineeTrainersListUpdateRequest) {
        // Get user by userName
        traineeService.updateTrainersList(traineeTrainersListUpdateRequest.getUserName(), traineeTrainersListUpdateRequest.getTrainers());

        // Get trainers list
        List<Trainer> trainers = traineeService.getTraineeByUserName(traineeTrainersListUpdateRequest.getUserName()).getTrainers();

        return new TraineeTrainersListUpdateResponse(trainers.stream().map(trainerMapper::trainerToTrainerDTOMapper).toList());
    }

    @GetMapping("/getNotAssignedOnTraineeTrainers")
    @Operation(summary = "Get not assigned on trainee trainers",
            description = "Obtains the list of trainers not assigned to concerned trainee",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Get not assigned on trainee trainers request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TraineeNotAssignedTrainersRequest.class)
                    )
            ))
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public List<TrainerDTO> getNotAssignedOnTraineeTrainers(@RequestBody TraineeNotAssignedTrainersRequest traineeNotAssignedTrainersRequest) {
        // Get list of trainers not assigned to trainee
        List<Trainer> trainers = traineeService.getNotAssignedTrainers(traineeNotAssignedTrainersRequest.getUserName());

        return trainers.stream().map(trainerMapper::trainerToTrainerDTOMapper).toList();
    }
}
