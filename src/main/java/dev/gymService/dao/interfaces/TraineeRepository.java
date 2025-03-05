package dev.gymService.dao.interfaces;

import dev.gymService.model.Trainee;

import java.util.List;

public interface TraineeRepository {
    Trainee create(Trainee trainee);

    Trainee getTraineeById(Long id);
    Trainee getTraineeByUserName(String userName);
    void changeTraineePassword(String userName,String newPassword);

    List<Trainee> findAll();
    Trainee updateTrainee(Trainee trainee);
    void delete(Long id);
}
