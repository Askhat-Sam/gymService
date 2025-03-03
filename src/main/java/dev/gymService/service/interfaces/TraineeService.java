package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;

public interface TraineeService {
    Trainee createTrainee(Trainee trainee);
//    Trainee updateTrainee(Trainee trainee);
//    void deleteTrainee(Long id);
    Trainee getTraineeById(Long id);
    Trainee getTraineeByUserName(String userName);
//    List<Trainee> getAllTrainee();
}
