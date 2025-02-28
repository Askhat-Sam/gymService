package dev.gymService.service.implementations;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.dao.TrainerDAO;
import dev.gymService.model.Trainer;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private final TrainerDAO trainerDAO;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);

    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        long userNameSuffix = 1;
        // Check if the userName is unique
        while (checkUserName(trainer.getUserName())) {
            trainer.setUserName(trainer.getUserName().concat(String.valueOf(userNameSuffix++)));
        }
        return trainerDAO.create(trainer);
    }

    private boolean checkUserName(String userName) {
        return trainerDAO.getAll().stream().anyMatch(t -> t.getUserName().equals(userName));
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        return trainerDAO.update(trainer);
    }

    @Override
    public void deleteTrainer(Long id) {
        trainerDAO.delete(id);
    }

    @Override
    public Trainer getTrainerById(Long id) {
        logger.log(Level.INFO, "The trainer  with id [" + id + "] has been requested");
        return trainerDAO.getById(id);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.getAll();
    }

    @Override
    public Long generateTrainerId() {
        return trainerDAO.generateTrainerId();
    }
}
