package dev.gymService.controller;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.FileLogger;
import org.springframework.stereotype.Controller;

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
    public void createTrainee(Trainee trainee){
        logger.log(Level.INFO, "Calling createTrainee method for trainee with id: " + trainee.getUserId());
        traineeService.createTrainee(trainee);
    }

    public void updateTrainee(Trainee trainee){
        logger.log(Level.INFO, "Calling updateTrainee method for trainee with id: " + trainee.getUserId());
        traineeService.updateTrainee(trainee);
    }

    public void deleteTrainee(Long id){
        logger.log(Level.INFO, "Calling deleteTrainee method for trainee with id: " + id);
        traineeService.deleteTrainee(id);
    }

    public void getTraineeById(Long id){
        logger.log(Level.INFO, "Calling getTraineeById method for trainee with id: " + id);
        traineeService.getTraineeById(id);
    }

    public void getAllTrainees(){
        logger.log(Level.INFO, "Calling getAllTrainees method");
        traineeService.getAllTrainee();
    }

    // Trainer methods
    public void createTrainer(Trainer trainer){
        logger.log(Level.INFO, "Calling createTrainer method for trainee with id: " + trainer.getUserId());
        trainerService.createTrainer(trainer);
    }

    public void updateTrainer(Trainer trainer){
        logger.log(Level.INFO, "Calling updateTrainer method for trainee with id: " + trainer.getUserId());
        trainerService.updateTrainer(trainer);
    }

    public void deleteTrainer(Long id){
        logger.log(Level.INFO, "Calling deleteTrainer method for trainee with id: " + id);
        trainerService.deleteTrainer(id);
    }

    public void getTrainerById(Long id){
        logger.log(Level.INFO, "Calling getTrainerById method for trainee with id: " + id);
        trainerService.getTrainerById(id);
    }

    public void getAllTrainers(){
        logger.log(Level.INFO, "Calling getAllTrainees method");
        trainerService.getAllTrainers();
    }

    // Training methods
    public void createTraining(Training training){
        logger.log(Level.INFO, "Calling createTraining method for trainee with id: " + training.getTrainingId());
        trainingService.createTraining(training);
    }

    public void updateTraining(Training training){
        logger.log(Level.INFO, "Calling updateTraining method for trainee with id: " + training.getTrainingId());
        trainingService.updateTraining(training);
    }

    public void deleteTraining(Long id){
        logger.log(Level.INFO, "Calling deleteTraining method for trainee with id: " + id);
        trainingService.deleteTraining(id);
    }

    public void getTrainingById(Long id){
        logger.log(Level.INFO, "Calling getTrainingById method for trainee with id: " + id);
        trainingService.getTrainingById(id);
    }

    public void getAllTrainings(){
        logger.log(Level.INFO, "Calling getAllTrainings method");
        trainingService.getAllTrainings();
    }

}
