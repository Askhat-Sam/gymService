package dev.gymService.controller;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.model.dto.TrainingAddRequest;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/gym-service/trainings")
public class TrainingController {
    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final Logger logger = Logger.getLogger("TrainingController");

    public TrainingController(TrainingService trainingService, TraineeService traineeService, TrainerService trainerService) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @PostMapping("/addTraining")
    public ResponseEntity<?> addTraining(@RequestBody TrainingAddRequest trainingAddRequest){
        // Get trainee/trainer by userName
        Trainee trainee = traineeService.getTraineeByUserName(trainingAddRequest.getTraineeUserName(), trainingAddRequest.getTraineePassword());
        Trainer trainer = trainerService.getTrainerByUserName(trainingAddRequest.getTrainerUsername(), trainingAddRequest.getTrainerPassword());
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

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + trainingAddRequest + " Response: " + ResponseEntity.ok());

        // Return response
        if (training != null) {
            return ResponseEntity.ok().body("{\"message\": \"Training has been added successfully\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"error\": \"Failure to add training\"}");
        }
    }

    @GetMapping("/getTrainingTypes")
    public List<TrainingType> getTrainingTypes(){
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + "No request body" + " Response: " + ResponseEntity.ok());

        return trainingService.getTrainingTypes();
    }
}
