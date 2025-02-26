package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;

import java.util.List;

public interface TrainerService {
    void createTrainer(Trainer trainer);
    void updateTrainer(Trainer trainer);
    void deleteTrainer(Long id);
    Trainer getTrainerById(Long id);
    List<Trainer> getAllTrainers();
}
