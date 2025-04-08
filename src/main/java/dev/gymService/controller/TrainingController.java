package dev.gymService.controller;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.model.dto.TrainingAddRequest;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gym-service/trainings")
public class TrainingController {
    private final TrainingService trainingService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    public TrainingController(TrainingService trainingService, TraineeService traineeService, TrainerService trainerService) {
        this.trainingService = trainingService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @PostMapping("/addTraining")
    public ResponseEntity<?> addTraining(@RequestBody TrainingAddRequest trainingAddRequest) {
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
        ResponseEntity<String> responseEntity;

        if (training != null) {
            responseEntity = ResponseEntity.ok().body("{\"message\": \"Training has been added successfully\"}");
            ;
        } else {
            responseEntity = ResponseEntity.badRequest().body("{\"error\": \"Failure to add training\"}");
        }

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                trainingAddRequest,
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return responseEntity;
    }

    @GetMapping("/getTrainingTypes")
    public List<TrainingType> getTrainingTypes() {
        List<TrainingType> trainingTypes = trainingService.getTrainingTypes();
        ResponseEntity<List<TrainingType>> responseEntity = ResponseEntity.ok().body(trainingTypes);

        // Get method name
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();

        // Log the rest call details: endpoint | request | service response | response message
        logger.info(" | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                "/gym-service/trainees/" + methodName,
                "Not request body required for this request",
                responseEntity.getStatusCode(),
                responseEntity.getBody());

        return trainingTypes;
    }
}
