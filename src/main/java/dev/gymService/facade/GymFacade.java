package dev.gymService.facade;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.FileLogger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
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
    public Trainee createTrainee(Trainee trainee) {
        return traineeService.createTrainee(trainee);
    }

    public Trainee updateTrainee(Trainee trainee) {
        return traineeService.updateTrainee(trainee);
    }

    public void deleteTrainee(Long id) {
        traineeService.deleteTrainee(id);
    }

    public Trainee getTraineeById(Long id) {
        return traineeService.getTraineeById(id);
    }

    public List<Trainee> getAllTrainees() {
        return traineeService.getAllTrainee();
    }

    // Trainer methods
    public Trainer createTrainer(Trainer trainer) {
        return trainerService.createTrainer(trainer);
    }

    public Trainer updateTrainer(Trainer trainer) {
        return trainerService.updateTrainer(trainer);
    }

    public void deleteTrainer(Long id) {
        trainerService.deleteTrainer(id);
    }

    public Trainer getTrainerById(Long id) {
        return trainerService.getTrainerById(id);
    }

    public List<Trainer> getAllTrainers() {
        return trainerService.getAllTrainers();
    }

    // Training methods
    public Training createTraining(Training training) {
        return trainingService.createTraining(training);
    }

    public Training updateTraining(Training training) {
        return trainingService.updateTraining(training);
    }

    public void deleteTraining(Long id) {
        trainingService.deleteTraining(id);
    }

    public Training getTrainingById(Long id) {
        return trainingService.getTrainingById(id);
    }

    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }
}
