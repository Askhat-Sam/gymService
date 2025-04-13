package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TrainerService extends UserService {
    Trainer createTrainer(Trainer trainer);

    Trainer getTrainerById(Long id, String userName, String password);

    Trainer getTrainerByUserName(String userName, String password);

    Boolean changeTrainerPassword(String userName, String oldPassword, String newPassword);

    Trainer updateTrainer(Trainer updatedTrainer, String userName, String password);

    Boolean changeTrainerStatus(String userName, String password);

    List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName, Long trainingTypeId);
}
