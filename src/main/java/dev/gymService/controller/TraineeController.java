package dev.gymService.controller;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.TraineeMapper;
import dev.gymService.utills.TrainerMapper;
import dev.gymService.utills.TrainingMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gym-service/trainees")
public class TraineeController {
    private final TraineeService traineeService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @PostMapping("/registerNewTrainee")
    @Operation(summary = "Register new trainee")
    public ResponseEntity<?> registerNewTrainee(@RequestBody TraineeRegistrationRequest traineeRegistrationRequest) {
        // Create new trainee
        Trainee trainee = new Trainee();
        trainee.setFirstName(traineeRegistrationRequest.getFirstName());
        trainee.setLastName(traineeRegistrationRequest.getLastName());
        trainee.setDateOfBirth(traineeRegistrationRequest.getDateOfBirth() != null ? traineeRegistrationRequest.getDateOfBirth() : null);
        trainee.setAddress(traineeRegistrationRequest.getAddress() != null ? traineeRegistrationRequest.getAddress() : null);
        trainee.setIsActive(true);

        // Persist the created trainee into DB
        Trainee createdTrainee = traineeService.createTrainee(trainee);
        var traineeRegistrationResponse = new TraineeRegistrationResponse(createdTrainee.getUserName(), createdTrainee.getPassword());

        // Prepare response entity
        ResponseEntity<TraineeRegistrationResponse> responseEntity = ResponseEntity.ok().body(traineeRegistrationResponse);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeRegistrationRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @GetMapping("/loginTrainee")
    public ResponseEntity<?> loginTrainee(@RequestBody TraineeLoginRequest traineeLoginRequest) {
        traineeService.getTraineeByUserName(traineeLoginRequest.getUserName(), traineeLoginRequest.getPassword());

        // Prepare response entity
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body("{\"message\": \"Login successful\"}");

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeLoginRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody TraineePasswordChangeRequest traineePasswordChangeRequest) {
        // Change user's password
        traineeService.changeTraineePassword(traineePasswordChangeRequest.getUserName(), traineePasswordChangeRequest.getOldPassword(),
                traineePasswordChangeRequest.getNewPassword());

        // Prepare response entity
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body("{\"message\": \"Password has been successfully changed\"}");

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineePasswordChangeRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @GetMapping("/getTraineeProfile")
    public TraineeProfileResponse getTraineeProfile(@RequestBody TraineeProfileRequest traineeProfileRequest) {
        // Get user by userName
        Trainee trainee = traineeService.getTraineeByUserName(traineeProfileRequest.getUserName(), traineeProfileRequest.getPassword());

        // Create TraineeProfileResponse
        TraineeProfileResponse traineeProfileResponse = TraineeMapper.INSTANCE.traineeToTraineeProfileResponse(trainee);

        // Set trainers list into trainee
        traineeProfileResponse.setTrainers(trainee.getTrainers().stream().map(TrainerMapper.INSTANCE::trainerToTrainerDTOMapper).collect(Collectors.toList()));

        // Prepare response entity
        ResponseEntity<TraineeProfileResponse> responseEntity = ResponseEntity.ok().body(traineeProfileResponse);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeProfileRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return traineeProfileResponse;
    }

    @PatchMapping("/toggleTraineeStatus")
    public ResponseEntity<?> toggleTraineeStatus(@RequestBody TraineeStatusToggleRequest traineeStatusToggleRequest) {
        // Change user's status
        Boolean isTraineeStatusToggled = traineeService.changeTraineeStatus(traineeStatusToggleRequest.getUserName(), traineeStatusToggleRequest.getPassword());

        // Prepare response entity
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body("{\"message\": \"User status has been successfully changed\"}");

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeStatusToggleRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @DeleteMapping("/deleteTrainee")
    public ResponseEntity<?> deleteTrainee(@RequestBody TraineeDeleteRequest traineeDeleteRequest) {
        // Change user's status
        Boolean isTraineeDeleted = traineeService.deleteTraineeByUserName(traineeDeleteRequest.getUserName(), traineeDeleteRequest.getPassword());

        // Prepare response entity
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body("{\"message\": \"User has been successfully deleted\"}");

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeDeleteRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @GetMapping("/getTraineeTrainings")
    public List<TrainingDTO> getTraineeTrainings(@RequestBody TraineeTrainingsRequest traineeTrainingsRequest) {
        // Handle optional parameters
        String fromDate = traineeTrainingsRequest.getFromDate() != null ? traineeTrainingsRequest.getFromDate() : "1990-01-01";
        String toDate = traineeTrainingsRequest.getToDate() != null ? traineeTrainingsRequest.getToDate() : "9999-01-01";
        String trainerName = traineeTrainingsRequest.getTrainerName() != null ? traineeTrainingsRequest.getTrainerName() : "";
        Long trainingTypeId = traineeTrainingsRequest.getTrainingType() != null ? traineeTrainingsRequest.getTrainingType() : 0;

        // Get trainings list for user
        List<Training> trainings = traineeService.getTraineeTrainingList(traineeTrainingsRequest.getUserName(), traineeTrainingsRequest.getPassword(), fromDate, toDate
                , trainerName, trainingTypeId);

        // Map result to List<TrainingDTO>
        List<TrainingDTO> result = trainings.stream().map(TrainingMapper.INSTANCE::trainingToTrainingDTOMapper).toList();

        // Prepare response entity
        ResponseEntity<List<TrainingDTO>> responseEntity = ResponseEntity.ok().body(result);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeTrainingsRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return result;
    }

    @PutMapping("/updateTrainee")
    public TraineeProfileUpdateResponse updateTrainee(@RequestBody TraineeProfileUpdateRequest traineeProfileUpdateRequest) {
        // Get user by userName
        Trainee trainee = traineeService.getTraineeByUserName(traineeProfileUpdateRequest.getUserName(), traineeProfileUpdateRequest.getPassword());

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
        Trainee updatedTrainee = traineeService.updateTrainee(trainee, traineeProfileUpdateRequest.getUserName(), traineeProfileUpdateRequest.getPassword());

        // Prepare response
        TraineeProfileUpdateResponse traineeProfileUpdateResponse = TraineeMapper.INSTANCE.traineeToTraineeProfileUpdateResponse(updatedTrainee);

        traineeProfileUpdateResponse.setTrainers(updatedTrainee.getTrainers().stream().map(TrainerMapper.INSTANCE::trainerToTrainerDTOMapper).toList());

        // Prepare response entity
        ResponseEntity<TraineeProfileUpdateResponse> responseEntity = ResponseEntity.ok().body(traineeProfileUpdateResponse);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeProfileUpdateRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return traineeProfileUpdateResponse;
    }


    @PutMapping("/updateTraineeTrainers")
    public TraineeTrainersListUpdateResponse updateTraineeTrainers(@RequestBody TraineeTrainersListUpdateRequest traineeTrainersListUpdateRequest) {
        // Get user by userName
        traineeService.updateTrainersList(traineeTrainersListUpdateRequest.getUserName(), traineeTrainersListUpdateRequest.getPassword(),
                traineeTrainersListUpdateRequest.getTrainers());

        // Get trainers list
        List<Trainer> trainers = traineeService.getTraineeByUserName(traineeTrainersListUpdateRequest.getUserName(),
                traineeTrainersListUpdateRequest.getPassword()).getTrainers();

        TraineeTrainersListUpdateResponse traineeTrainersListUpdateResponse = new TraineeTrainersListUpdateResponse(trainers.stream().map(TrainerMapper.INSTANCE::trainerToTrainerDTOMapper).toList());

        // Prepare response entity
        ResponseEntity<TraineeTrainersListUpdateResponse> responseEntity = ResponseEntity.ok().body(traineeTrainersListUpdateResponse);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeTrainersListUpdateRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return traineeTrainersListUpdateResponse;
    }

    @GetMapping("/getNotAssignedOnTraineeTrainers")
    public List<TrainerDTO> getNotAssignedOnTraineeTrainers(@RequestBody TraineeNotAssignedTrainersRequest traineeNotAssignedTrainersRequest) {
        // Get list of trainers not assigned to trainee
        List<Trainer> trainers = traineeService.getNotAssignedTrainers(traineeNotAssignedTrainersRequest.getUserName(), traineeNotAssignedTrainersRequest.getPassword());

        // Map the trainers list to trainerDTO list
        List<TrainerDTO> result = trainers.stream().map(TrainerMapper.INSTANCE::trainerToTrainerDTOMapper).toList();

        // Prepare response entity
        ResponseEntity<List<TrainerDTO>> responseEntity = ResponseEntity.ok().body(result);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                traineeNotAssignedTrainersRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return result;
    }
}
