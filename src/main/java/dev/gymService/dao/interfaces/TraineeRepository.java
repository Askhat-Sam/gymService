package dev.gymService.dao.interfaces;

import dev.gymService.model.Trainee;
import dev.gymService.model.Training;

import java.util.List;

public interface TraineeRepository {
    Trainee create(Trainee trainee);

    Trainee getTraineeById(Long id);
    Trainee getTraineeByUserName(String userName);
    void changeTraineePassword(String userName,String newPassword);

    void deleteTraineeByUserName(String userName);
    void changeTraineeStatus(String userName);

    List<Training> getTraineeTrainings(String userName, String fromDate, String toDate, String trainerUserName);





    List<Trainee> findAll();
    Trainee updateTrainee(Trainee trainee);
    void delete(Long id);
}
