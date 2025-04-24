package dev.gymService.repository.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import java.util.List;

public interface TrainerRepository extends UserRepository<Trainer> {

    List<Training> getTrainerTrainingList(String trainerName, String fromDate, String toDate, String traineeName, Long trainingTypeId);
}
