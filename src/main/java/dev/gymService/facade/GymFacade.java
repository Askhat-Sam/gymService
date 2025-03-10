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

    // ### - Trainee methods - ##
    public Trainee createTrainee(Trainee trainee) {
        return traineeService.createTrainee(trainee);
    }

    public Trainee getTraineeById(Long id, String userName, String password) {
        return traineeService.getTraineeById(id, userName, password);
    }

    public Trainee getTraineeByUserName(String userName, String password) {
        return traineeService.getTraineeByUserName(userName, password);
    }

    public void changeTraineePassword(String userName, String oldPassword, String newPassword) {
        traineeService.changeTraineePassword(userName, oldPassword, newPassword);
    }

    public Trainee updateTrainee(Trainee trainee){
        return traineeService.updateTrainee(trainee);
    }

    public void deleteTraineeByUserName(String userName, String password){
        traineeService.deleteTraineeByUserName(userName, password);
    }

    public void changeTraineeStatus(String userName, String password){
        traineeService.changeTraineeStatus(userName, password);
    }

    public List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName){
        return traineeService.getTraineeTrainingList(traineeName, password, fromDate, toDate, trainerName);
    }

    public List<Trainer> getNotAssignedTrainers(String traineeUserName, String password){
        return traineeService.getNotAssignedTrainers(traineeUserName, password);
    }

    public void updateTrainersList(String traineeUserName, String password, List<Trainer> trainers){
        traineeService.updateTrainersList(traineeUserName, password, trainers);
    }




    // ### - Trainer methods - ##

    public Trainer createTrainer(Trainer trainer){
        return trainerService.createTrainer(trainer);
    }
    public Trainer getTrainerById(Long id, String userName, String password){
        return trainerService.getTrainerById(id, userName, password);
    }
    public Trainer getTrainerByUserName(String userName, String password){
        return trainerService.getTrainerByUserName(userName, password);
    }
    public void changeTrainerPassword(String userName, String oldPassword, String newPassword){
        trainerService.changeTrainerPassword(userName, oldPassword, newPassword);
    }
    public Trainer updateTrainer(Trainer trainer){
        return trainerService.updateTrainer(trainer);
    }
    public void changeTrainerStatus(String userName, String password){
        trainerService.changeTrainerStatus(userName, password);
    }
    public List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName){
        return trainerService.getTrainerTrainingList(trainerName, password, fromDate, toDate, traineeName);
    }

    // ### - Trainings methods - ##

    public Training addTraining(Training training){
        return trainingService.addTraining(training);
    }
}
