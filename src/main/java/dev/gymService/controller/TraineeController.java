package dev.gymService.controller;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.TrainerDTOMapper;
import dev.gymService.utills.TrainingDTOMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gym-service/trainees")
public class TraineeController {
    private final TraineeService traineeService;
    private final Logger logger = Logger.getLogger("TraineeController");

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
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeRegistrationRequest + " Response: " + ResponseEntity.ok());


        return ResponseEntity.ok().body(traineeRegistrationResponse);
    }

    @GetMapping("/loginTrainee")
    public ResponseEntity<?> loginTrainee(@RequestBody TraineeLoginRequest traineeLoginRequest) {
        traineeService.getTraineeByUserName(traineeLoginRequest.getUserName(), traineeLoginRequest.getPassword());
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeLoginRequest + " Response: " + ResponseEntity.ok());
        // Return response
        return ResponseEntity.ok().body("{\"message\": \"Login successful\"}");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody TraineePasswordChangeRequest traineePasswordChangeRequest) {
        // Change user's password
        traineeService.changeTraineePassword(traineePasswordChangeRequest.getUserName(), traineePasswordChangeRequest.getOldPassword(),
                traineePasswordChangeRequest.getNewPassword());
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineePasswordChangeRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return ResponseEntity.ok().body("{\"message\": \"Password has been successfully changed\"}");

    }

    @GetMapping("/getTraineeProfile")
    public TraineeProfileResponse getTraineeProfile(@RequestBody TraineeProfileRequest traineeProfileRequest) {
        // Get user by userName
        Trainee trainee = traineeService.getTraineeByUserName(traineeProfileRequest.getUserName(), traineeProfileRequest.getPassword());

        // Create TraineeProfileResponse
        TraineeProfileResponse traineeProfileResponse = new TraineeProfileResponse(
                trainee.getFirstName(),
                trainee.getLastName(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                trainee.getIsActive()
        );

        // Set trainers list into trainee
        traineeProfileResponse.setTrainers(trainee.getTrainers().stream().map(TrainerDTOMapper::toTrainerDTO).collect(Collectors.toList()));

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeProfileRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return traineeProfileResponse;
    }

    @PatchMapping("/toggleTraineeStatus")
    public ResponseEntity<?> toggleTraineeStatus(@RequestBody TraineeStatusToggleRequest traineeStatusToggleRequest) {
        // Change user's status
        Boolean isTraineeStatusToggled = traineeService.changeTraineeStatus(traineeStatusToggleRequest.getUserName(), traineeStatusToggleRequest.getPassword());

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeStatusToggleRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return ResponseEntity.ok().body("{\"message\": \"User status has been successfully changed\"}");
    }

    @DeleteMapping("/deleteTrainee")
    public ResponseEntity<?> deleteTrainee(@RequestBody TraineeDeleteRequest traineeDeleteRequest) {
        // Change user's status
        Boolean isTraineeDeleted = traineeService.deleteTraineeByUserName(traineeDeleteRequest.getUserName(), traineeDeleteRequest.getPassword());

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeDeleteRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return ResponseEntity.ok().body("{\"message\": \"User has been successfully deleted\"}");
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

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeTrainingsRequest + " Response: " + ResponseEntity.ok());

        // Map result to List<TrainingDTO>
        List<TrainingDTO> result = trainings.stream().map(TrainingDTOMapper::toTrainingDTO).toList();

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
        TraineeProfileUpdateResponse traineeProfileUpdateResponse = new TraineeProfileUpdateResponse();
        traineeProfileUpdateResponse.setUserName(updatedTrainee.getUserName());
        traineeProfileUpdateResponse.setFirstName(updatedTrainee.getFirstName());
        traineeProfileUpdateResponse.setLastName(updatedTrainee.getLastName());
        traineeProfileUpdateResponse.setDateOfBirth(updatedTrainee.getDateOfBirth());
        traineeProfileUpdateResponse.setAddress(updatedTrainee.getAddress());
        traineeProfileUpdateResponse.setIsActive(updatedTrainee.getIsActive());
        traineeProfileUpdateResponse.setTrainers(updatedTrainee.getTrainers().stream().map(TrainerDTOMapper::toTrainerDTO).toList());

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeProfileUpdateRequest + " Response: " + ResponseEntity.ok());

        // Return response
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

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeTrainersListUpdateRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return new TraineeTrainersListUpdateResponse(trainers.stream().map(TrainerDTOMapper::toTrainerDTO).toList());
    }

    @GetMapping("/getNotAssignedOnTraineeTrainers")
    public List<TrainerDTO> getNotAssignedOnTraineeTrainers(@RequestBody TraineeNotAssignedTrainersRequest traineeNotAssignedTrainersRequest) {
        // Get list of trainers not assigned to trainee
        List<Trainer> trainers = traineeService.getNotAssignedTrainers(traineeNotAssignedTrainersRequest.getUserName(), traineeNotAssignedTrainersRequest.getPassword());

        // Map the trainers list to trainerDTO list
        List<TrainerDTO> result = trainers.stream().map(TrainerDTOMapper::toTrainerDTO).toList();

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + traineeNotAssignedTrainersRequest + " Response: " + ResponseEntity.ok());

        return result;
    }
}
