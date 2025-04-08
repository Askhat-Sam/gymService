package dev.gymService.controller;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.TraineeMapper;
import dev.gymService.utills.TrainerMapper;
import dev.gymService.utills.TrainingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gym-service/trainers")
public class TrainerController {
    private final TrainerService trainerService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/registerNewTrainer")
    public TrainerRegistrationResponse registerNewTrainer(@RequestBody TrainerRegistrationRequest trainerRegistrationRequest) {
        // Create new trainer
        Trainer trainer = new Trainer();
        trainer.setFirstName(trainerRegistrationRequest.getFirstName());
        trainer.setLastName(trainerRegistrationRequest.getLastName());
        trainer.setSpecialization(trainerRegistrationRequest.getSpecialization());
        trainer.setIsActive(true);

        // Persist the created trainer into DB
        Trainer createdTrainer = trainerService.createTrainer(trainer);

        TrainerRegistrationResponse trainerRegistrationResponse = new TrainerRegistrationResponse(createdTrainer.getUserName(), createdTrainer.getPassword());

        // Prepare response entity
        ResponseEntity<TrainerRegistrationResponse> responseEntity = ResponseEntity.ok().body(trainerRegistrationResponse);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainerRegistrationRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return trainerRegistrationResponse;
    }

    @GetMapping("/loginTrainer")
    public ResponseEntity<?> loginTrainer(@RequestBody TrainerLoginRequest trainerLoginRequest) {

        trainerService.getTrainerByUserName(trainerLoginRequest.getUserName(), trainerLoginRequest.getPassword());

        // Prepare response entity
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body("{\"message\": \"Login successful\"}");

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainerLoginRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody TrainerPasswordChangeRequest trainerPasswordChangeRequest) {
        // Change user's password
        trainerService.changeTrainerPassword(trainerPasswordChangeRequest.getUserName(), trainerPasswordChangeRequest.getOldPassword(),
                trainerPasswordChangeRequest.getNewPassword());

        // Prepare response entity
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body("{\"message\": \"Password has been successfully changed\"}");

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainerPasswordChangeRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @GetMapping("/getTrainerByUsername")
    public TrainerProfileResponse getTrainerProfile(@RequestBody TrainerProfileRequest trainerProfileRequest) {
        // Get user by userName
        Trainer trainer = trainerService.getTrainerByUserName(trainerProfileRequest.getUserName(), trainerProfileRequest.getPassword());

        // Create TraineeProfileResponse
        TrainerProfileResponse trainerProfileResponse = TrainerMapper.INSTANCE.trainerToTrainerProfileResponseMapper(trainer);

        // Set trainees list into trainerProfileResponse
        trainerProfileResponse.setTrainees(trainer.getTrainees().stream().map(TraineeMapper.INSTANCE::traineeToTraineeDTOMapper).collect(Collectors.toList()));

        // Prepare response entity
        ResponseEntity<TrainerProfileResponse> responseEntity = ResponseEntity.ok().body(trainerProfileResponse);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainerProfileRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return trainerProfileResponse;
    }

    @PatchMapping("/toggleTrainerStatus")
    public ResponseEntity<?> toggleTrainerStatus(@RequestBody TrainerStatusToggleRequest trainerStatusToggleRequest) {
        // Change user's status
        trainerService.changeTrainerStatus(trainerStatusToggleRequest.getUserName(), trainerStatusToggleRequest.getPassword());

        // Prepare response entity
        ResponseEntity<String> responseEntity = ResponseEntity.ok().body("{\"message\": \"User status has been successfully changed\"}");

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainerStatusToggleRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @GetMapping("/getTrainerTrainings")
    public List<TrainingDTO> getTraineeTrainings(@RequestBody TrainerTrainingsRequest trainerTrainingsRequest) {
        // Handle optional parameters
        String fromDate = trainerTrainingsRequest.getFromDate() != null ? trainerTrainingsRequest.getFromDate() : "1990-01-01";
        String toDate = trainerTrainingsRequest.getToDate() != null ? trainerTrainingsRequest.getToDate() : "9999-01-01";
        String traineeName = trainerTrainingsRequest.getTraineeName() != null ? trainerTrainingsRequest.getTraineeName() : "";
        Long trainingTypeId = trainerTrainingsRequest.getTrainingType() != null ? trainerTrainingsRequest.getTrainingType() : 0;
        // Get trainings list for user
        List<Training> trainings = trainerService.getTrainerTrainingList(trainerTrainingsRequest.getUserName(), trainerTrainingsRequest.getPassword(), fromDate, toDate
                , traineeName, trainingTypeId);

        // Map result to List<TrainingDTO>
        List<TrainingDTO> result = trainings.stream().map(TrainingMapper.INSTANCE::trainingToTrainingDTOMapper).toList();

        // Prepare response entity
        ResponseEntity<List<TrainingDTO>> responseEntity = ResponseEntity.ok().body(result);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainerTrainingsRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return result;
    }

    @PutMapping("/updateTrainer")
    public TrainerProfileUpdateResponse getTrainerProfile(@RequestBody TrainerProfileUpdateRequest trainerProfileUpdateRequest) {
        // Get user by userName
        Trainer trainer = trainerService.getTrainerByUserName(trainerProfileUpdateRequest.getUserName(), trainerProfileUpdateRequest.getPassword());

        // Update existing trainee
        trainer.setFirstName(trainerProfileUpdateRequest.getFirstName());
        trainer.setLastName(trainerProfileUpdateRequest.getLastName());
        if (trainerProfileUpdateRequest.getSpecialization() != null) {
            trainer.setSpecialization(trainerProfileUpdateRequest.getSpecialization());
        }
        trainer.setIsActive(trainerProfileUpdateRequest.getIsActive());

        // Update user
        Trainer updatedTrainer = trainerService.updateTrainer(trainer, trainerProfileUpdateRequest.getUserName(), trainerProfileUpdateRequest.getPassword());

        // Prepare response
        TrainerProfileUpdateResponse trainerProfileUpdateResponse = TrainerMapper.INSTANCE.trainerToTrainerProfileUpdateResponseMapper(updatedTrainer);

        trainerProfileUpdateResponse.setTrainers(updatedTrainer.getTrainees().stream().map(TraineeMapper.INSTANCE::traineeToTraineeDTOMapper).toList());

        // Prepare response entity
        ResponseEntity<TrainerProfileUpdateResponse> responseEntity = ResponseEntity.ok().body(trainerProfileUpdateResponse);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainerProfileUpdateRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return trainerProfileUpdateResponse;
    }
}
