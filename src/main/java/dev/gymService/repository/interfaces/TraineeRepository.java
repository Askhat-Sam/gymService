package dev.gymService.repository.interfaces;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TraineeRepository {
    Trainee create(Trainee trainee);

    Trainee updateTrainee(Trainee trainee);

    Trainee getTraineeById(Long id);

    Trainee getTraineeByUserName(String userName);

    void deleteTraineeByUserName(String userName);

    List<Training> getTraineeTrainingList(String traineeName, String fromDate, String toDate, String trainerName);

    List<Trainer> getNotAssignedTrainers(String traineeUserName);

}
