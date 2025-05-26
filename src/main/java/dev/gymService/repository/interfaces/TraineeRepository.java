package dev.gymService.repository.interfaces;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TraineeRepository extends UserRepository<Trainee> {

    List<Training> getTraineeTrainingList(String traineeName, String fromDate, String toDate, String trainerName, Long trainingType);

    List<Trainer> getNotAssignedTrainers(String traineeUserName);

}
