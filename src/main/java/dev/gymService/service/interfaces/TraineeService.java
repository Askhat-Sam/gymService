package dev.gymService.service.interfaces;

import dev.gymService.model.Trainee;

import java.util.Date;
import java.util.List;

public interface TraineeService {
    void createTrainee(Trainee trainee);
    void updateTrainee(Trainee trainee);
    void deleteTrainee(Long id);
    Trainee getTraineeById(Long id);
    List<Trainee> getAllTrainee();
}
