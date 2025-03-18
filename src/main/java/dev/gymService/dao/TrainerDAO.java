package dev.gymService.dao;

import dev.gymService.model.Trainer;
import dev.gymService.storage.InMemoryStorage;
import dev.gymService.utills.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TrainerDAO extends Dao<Trainer>{
    private InMemoryStorage inMemoryStorage;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);
    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage){
        this.inMemoryStorage = inMemoryStorage;
    }

    public Trainer getById(Long id) {
        return inMemoryStorage.getTrainerStorage().get(id);
    }

    public Trainer create(Trainer trainer) {
        trainer.setUserId(generateTrainerId());
        inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
        logger.log(Level.INFO, "New trainer  with id [" + trainer.getUserId() + "] has been created");
        return inMemoryStorage.getTrainerStorage().get(trainer.getUserId());
    }
    public Trainer update(Trainer trainer) {
        inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
        logger.log(Level.INFO, "The trainer  with id [" + trainer.getUserId() + "] has been updated");
        return inMemoryStorage.getTrainerStorage().get(trainer.getUserId());
    }
    public void delete(Long id) {
        inMemoryStorage.getTrainerStorage().remove(id);
        logger.log(Level.INFO, "The trainer  with id [" + id + "] has been deleted");
    }
    public List<Trainer> getAll(){
        logger.log(Level.INFO, "The list of trainers has been requested");
        return new ArrayList<>(inMemoryStorage.getTrainerStorage().values());
    }

    public Long generateTrainerId() {
        return generateId(Trainer::getUserId);
    }

    public Trainer getTrainerByUserName(String userName) {
        return inMemoryStorage.getTrainerStorage().values()
                .stream()
                .filter(trainer -> userName.equals(trainer.getUserName()))
                .findFirst()
                .orElse(null);
    }
}
