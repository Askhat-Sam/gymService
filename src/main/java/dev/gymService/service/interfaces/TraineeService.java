package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;

import java.util.List;

public interface TraineeService {
    Trainee createTrainee(Trainee trainee);
    Trainee getTraineeById(Long id);
    Trainee getTraineeByUserName(String userName);
    Trainee updateTrainee(Trainee trainee);

    void changeTraineePassword(String userName,String newPassword);
    //    Trainee updateTrainee(Trainee trainee);
    //    void deleteTrainee(Long id);
//    List<Trainee> getAllTrainee();
//
}
