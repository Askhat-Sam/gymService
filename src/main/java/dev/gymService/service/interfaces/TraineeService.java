package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TraineeService extends UserService{
    Trainee createTrainee(Trainee trainee);

    Trainee getTraineeById(Long id, String userName, String password);

    Trainee getTraineeByUserName(String userName, String password);

    Boolean changeTraineePassword(String userName, String oldPassword, String newPassword);

    Boolean deleteTraineeByUserName(String userName, String password);

    Boolean changeTraineeStatus(String userName, String password);

    List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName, Long trainingTypeId);

    Trainee updateTrainee(Trainee updatedTrainee, String userName, String password);

    List<Trainer> getNotAssignedTrainers(String traineeUserName, String password);

    List<Trainer> updateTrainersList(String traineeUserName, String password, List<Trainer> trainers);
}
