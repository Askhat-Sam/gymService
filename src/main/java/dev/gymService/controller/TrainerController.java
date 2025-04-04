package dev.gymService.controller;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.*;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.TraineeDTOMapper;
import dev.gymService.utills.TrainingDTOMapper;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gym-service/trainers")
public class TrainerController {
    private final TrainerService trainerService;
    private final Logger logger = Logger.getLogger("TrainerController");


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

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainerRegistrationRequest + " Response: " + ResponseEntity.ok());

        return new TrainerRegistrationResponse(createdTrainer.getUserName(), createdTrainer.getPassword());
    }

    @GetMapping("/loginTrainer")
    public ResponseEntity<?> loginTrainer(@RequestBody TrainerLoginRequest trainerLoginRequest) {
        // Check if the user is exist in DB
        Trainer trainer = trainerService.getTrainerByUserName(trainerLoginRequest.getUserName(), trainerLoginRequest.getPassword());

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainerLoginRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return ResponseEntity.ok().body("{\"message\": \"Login successful\"}");
    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody TrainerPasswordChangeRequest trainerPasswordChangeRequest) {
        // Change user's password
        Boolean isPasswordChanged = trainerService.changeTrainerPassword(trainerPasswordChangeRequest.getUserName(), trainerPasswordChangeRequest.getOldPassword(),
                trainerPasswordChangeRequest.getNewPassword());

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainerPasswordChangeRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return ResponseEntity.ok().body("{\"message\": \"Password has been successfully changed\"}");

    }

    @GetMapping("/getTrainerByUsername")
    public TrainerProfileResponse getTrainerProfile(@RequestBody TrainerProfileRequest trainerProfileRequest) {
        // Get user by userName
        Trainer trainer = trainerService.getTrainerByUserName(trainerProfileRequest.getUserName(), trainerProfileRequest.getPassword());

        // Create TraineeProfileResponse
        TrainerProfileResponse trainerProfileResponse = new TrainerProfileResponse(
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getSpecialization(),
                trainer.getIsActive()
        );

        // Set trainees list into trainerProfileResponse
        trainerProfileResponse.setTrainees(trainer.getTrainees().stream().map(TraineeDTOMapper::toTraineeDTO).collect(Collectors.toList()));

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainerProfileRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return trainerProfileResponse;
    }

    @PatchMapping("/toggleTrainerStatus")
    public ResponseEntity<?> toggleTrainerStatus(@RequestBody TrainerStatusToggleRequest trainerStatusToggleRequest) {
        // Change user's status
        Boolean isTrainerStatusToggled = trainerService.changeTrainerStatus(trainerStatusToggleRequest.getUserName(), trainerStatusToggleRequest.getPassword());

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainerStatusToggleRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return ResponseEntity.ok().body("{\"message\": \"User status has been successfully changed\"}");
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
        List<TrainingDTO> result = trainings.stream().map(TrainingDTOMapper::toTrainingDTO).toList();

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainerTrainingsRequest + " Response: " + ResponseEntity.ok());

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
        TrainerProfileUpdateResponse trainerProfileUpdateResponse = new TrainerProfileUpdateResponse();
        trainerProfileUpdateResponse.setUserName(updatedTrainer.getUserName());
        trainerProfileUpdateResponse.setFirstName(updatedTrainer.getFirstName());
        trainerProfileUpdateResponse.setLastName(updatedTrainer.getLastName());
        trainerProfileUpdateResponse.setSpecialization(updatedTrainer.getSpecialization());
        trainerProfileUpdateResponse.setIsActive(updatedTrainer.getIsActive());
        trainerProfileUpdateResponse.setTrainers(updatedTrainer.getTrainees().stream().map(TraineeDTOMapper::toTraineeDTO).toList());

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainerProfileUpdateRequest + " Response: " + ResponseEntity.ok());

        // Return response
        return trainerProfileUpdateResponse;
    }
}
