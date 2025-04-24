package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TrainerService {
    Trainer createTrainer(Trainer trainer);

    Trainer getTrainerById(Long id);

    Trainer getTrainerByUserName(String userName);

    Boolean changeTrainerPassword(String userName, String newPassword);

    Trainer updateTrainer(Trainer updatedTrainer, String userName);

    Boolean changeTrainerStatus(String userName);

    List<Training> getTrainerTrainingList(String trainerName, String fromDate, String toDate, String traineeName, Long trainingTypeId);
}
