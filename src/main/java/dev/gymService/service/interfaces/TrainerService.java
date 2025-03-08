package dev.gymService.service.interfaces;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;

import javax.swing.event.ListDataListener;
import java.util.List;

public interface TrainerService {
    Trainer createTrainer(Trainer trainer);
    Trainer getTrainerById(Long id);
    Trainer getTrainerByUserName(String userName);
    void changeTrainerPassword(String userName,String newPassword);
    Trainer updateTrainer(Trainer trainer);
    void changeTrainerStatus(String userName);




//    Trainer updateTrainer(Trainer trainer);
//    void deleteTrainer(Long id);
//    Trainer getTrainerById(Long id);
//    List<Trainer> getAllTrainers();

}
