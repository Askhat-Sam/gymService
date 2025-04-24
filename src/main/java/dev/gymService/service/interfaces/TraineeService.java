package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import java.util.List;

public interface TraineeService{
    Trainee createTrainee(Trainee trainee);

    Trainee getTraineeById(Long id);

    Trainee getTraineeByUserName(String userName);

    Boolean changeTraineePassword(String userName, String newPassword);

    Boolean deleteTraineeByUserName(String userName);

    Boolean changeTraineeStatus(String userName);

    List<Training> getTraineeTrainingList(String traineeName, String fromDate, String toDate, String trainerName, Long trainingTypeId);

    Trainee updateTrainee(Trainee updatedTrainee, String userName);

    List<Trainer> getNotAssignedTrainers(String traineeUserName);

    List<Trainer> updateTrainersList(String traineeUserName, List<Trainer> trainers);
}
