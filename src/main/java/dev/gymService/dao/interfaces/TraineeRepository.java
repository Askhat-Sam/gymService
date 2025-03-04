package dev.gymService.dao.interfaces;

import dev.gymService.model.Trainee;

import java.util.List;

public interface TraineeRepository {
    Trainee create(Trainee trainee);

    Trainee getTraineeById(Long id);
    Trainee getTraineeByUserName(String userName);

    List<Trainee> findAll();
    Trainee update(Trainee trainee);
    void delete(Long id);
}
