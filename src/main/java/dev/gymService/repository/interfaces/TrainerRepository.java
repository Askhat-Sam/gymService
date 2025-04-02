package dev.gymService.repository.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TrainerRepository {
    Trainer create(Trainer trainer);

    Trainer getTrainerById(Long id);

    Trainer getTrainerByUserName(String userName);

    Trainer updateTrainer(Trainer trainer);

    List<Training> getTrainerTrainingList(String trainerName, String fromDate, String toDate, String traineeName, Long trainingTypeId);
}
