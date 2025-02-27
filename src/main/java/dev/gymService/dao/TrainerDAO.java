package dev.gymService.dao;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.storage.InMemoryStorage;
import dev.gymService.utills.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TrainerDAO {
    private InMemoryStorage inMemoryStorage;
    private static final Logger logger = FileLogger.getLogger(TrainerDAO.class);
    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage){
        this.inMemoryStorage = inMemoryStorage;
    }

    public Trainer getById(Long id) {
        logger.info("Getting trainer with id: " + id);
        return inMemoryStorage.getTrainerStorage().get(id);
    }

    public void create(Trainer trainer) {
        logger.info("Creating new trainer with id: " + trainer.getUserId());
        inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
    }
    public void update(Trainer trainer) {
        logger.info("Updating trainer with id: " + trainer.getUserId());
        inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
    }
    public void delete(Long id) {
        logger.info("Deleting trainer with id: " + id);
        inMemoryStorage.getTrainerStorage().remove(id);
    }
    public List<Trainer> getAll(){
        logger.info("Getting the list of all trainers");
        return new ArrayList<>(inMemoryStorage.getTrainerStorage().values());
    }
}
