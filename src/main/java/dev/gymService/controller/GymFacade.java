package dev.gymService.controller;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.FileLogger;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class GymFacade {
    TraineeService traineeService;
    TrainerService trainerService;
    TrainingService trainingService;

    private static final Logger logger = FileLogger.getLogger(GymFacade.class);

    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }
    // Trainee methods
    public Trainee createTrainee(Trainee trainee){
        logger.log(Level.INFO, "Calling createTrainee method for trainee with id: " + trainee.getUserId());
        return traineeService.createTrainee(trainee);
    }

    public Trainee updateTrainee(Trainee trainee){
        logger.log(Level.INFO, "Calling updateTrainee method for trainee with id: " + trainee.getUserId());
        return traineeService.updateTrainee(trainee);
    }

    public void deleteTrainee(Long id){
        logger.log(Level.INFO, "Calling deleteTrainee method for trainee with id: " + id);
        traineeService.deleteTrainee(id);
    }

    public Trainee getTraineeById(Long id){
        logger.log(Level.INFO, "Calling getTraineeById method for trainee with id: " + id);
        return traineeService.getTraineeById(id);
    }

    public List<Trainee> getAllTrainees(){
        logger.log(Level.INFO, "Calling getAllTrainees method");
        return traineeService.getAllTrainee();
    }

    // Trainer methods
    public Trainer createTrainer(Trainer trainer){
        logger.log(Level.INFO, "Calling createTrainer method for trainee with id: " + trainer.getUserId());
        return trainerService.createTrainer(trainer);
    }

    public Trainer updateTrainer(Trainer trainer){
        logger.log(Level.INFO, "Calling updateTrainer method for trainee with id: " + trainer.getUserId());
        return trainerService.updateTrainer(trainer);
    }

    public void deleteTrainer(Long id){
        logger.log(Level.INFO, "Calling deleteTrainer method for trainee with id: " + id);
        trainerService.deleteTrainer(id);
    }

    public Trainer getTrainerById(Long id){
        logger.log(Level.INFO, "Calling getTrainerById method for trainee with id: " + id);
        return trainerService.getTrainerById(id);
    }

    public List<Trainer> getAllTrainers(){
        logger.log(Level.INFO, "Calling getAllTrainees method");
        return trainerService.getAllTrainers();
    }

    // Training methods
    public Training createTraining(Training training){
        logger.log(Level.INFO, "Calling createTraining method for trainee with id: " + training.getTrainingId());
        return trainingService.createTraining(training);
    }

    public Training updateTraining(Training training){
        logger.log(Level.INFO, "Calling updateTraining method for trainee with id: " + training.getTrainingId());
        return trainingService.updateTraining(training);
    }

    public void deleteTraining(Long id){
        logger.log(Level.INFO, "Calling deleteTraining method for trainee with id: " + id);
        trainingService.deleteTraining(id);
    }

    public Training getTrainingById(Long id){
        logger.log(Level.INFO, "Calling getTrainingById method for trainee with id: " + id);
        return trainingService.getTrainingById(id);
    }

    public List<Training> getAllTrainings(){
        logger.log(Level.INFO, "Calling getAllTrainings method");
        return trainingService.getAllTrainings();
    }

}
