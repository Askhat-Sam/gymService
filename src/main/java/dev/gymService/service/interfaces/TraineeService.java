package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;

import java.util.List;

public interface TraineeService {
    Trainee createTrainee(Trainee trainee);
//    Trainee updateTrainee(Trainee trainee);
//    void deleteTrainee(Long id);
    Trainee getTraineeById(Long id);
//    List<Trainee> getAllTrainee();
//
//    Trainee getTraineeByUserName(String userName);
}
