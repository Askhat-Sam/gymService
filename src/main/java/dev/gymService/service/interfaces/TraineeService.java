package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;

import java.util.Date;

public interface TraineeService {
    Trainee createTrainee(String firstName, String lastName, String userName, String password, Boolean isActive,
                          Date dateOfBirth, String address, String userId);
    void updateTrainee(String userId);
    void deleteTrainee(String userId);
    Trainee selectTrainee(Long userId);
}
