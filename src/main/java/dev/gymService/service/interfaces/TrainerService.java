package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import javax.swing.event.ListDataListener;
import java.util.List;

public interface TrainerService {
    Trainer createTrainer(Trainer trainer);

    Trainer getTrainerById(Long id, String userName, String password);

    Trainer getTrainerByUserName(String userName, String password);

    void changeTrainerPassword(String userName, String oldPassword, String newPassword);

    Trainer updateTrainer(Trainer updatedTrainer, String userName, String password);

    void changeTrainerStatus(String userName, String password);

    List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName);
}
