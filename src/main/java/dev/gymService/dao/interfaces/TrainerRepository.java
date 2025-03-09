package dev.gymService.dao.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TrainerRepository {
    Trainer create(Trainer trainer);
    Trainer getTrainerById(Long id, String userName, String password);
    Trainer getTrainerByUserName(String userName, String password);
    void changeTrainerPassword(String userName, String oldPassword, String newPassword);
    Trainer updateTrainee(Trainer trainer);
    public void changeTrainerStatus(String userName, String password);
    List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName);
}
