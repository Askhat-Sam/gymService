package dev.gymService.dao.interfaces;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;

import java.util.List;

public interface TrainerRepository {
    Trainer create(Trainer trainer);

    Trainer getTrainerById(Long id);
    Trainer getTrainerByUserName(String userName);

    void changeTrainerPassword(String userName,String newPassword);
    Trainer updateTrainee(Trainer trainer);

    List<Trainer> findAll();
    Trainer update(Trainer trainer);
    void delete(Long id);
}
