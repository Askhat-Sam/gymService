package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;

public interface TrainerService {
    Trainer createTrainer(String firstName, String lastName, String userName, String password, Boolean isActive, String specialization, String userId);
    void updateTrainer(String userId);
    void selectTrainer(String userId);
}
