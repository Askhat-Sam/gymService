package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TraineeService {
    Trainee createTrainee(Trainee trainee);

    Trainee getTraineeById(Long id, String userName, String password);

    Trainee getTraineeByUserName(String userName, String password);

    void changeTraineePassword(String userName, String oldPassword, String newPassword);

    Trainee updateTrainee(Trainee trainee);

    void deleteTraineeByUserName(String userName, String password);

    void changeTraineeStatus(String userName, String password);

    List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName);

    List<Trainer> getNotAssignedTrainers(String traineeUserName, String password);

    void updateTrainersList(String traineeUserName, String password, List<Trainer> trainers);
}
