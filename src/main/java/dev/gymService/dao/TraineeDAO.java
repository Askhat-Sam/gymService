package dev.gymService.dao;

import dev.gymService.model.Trainee;
import dev.gymService.storage.InMemoryStorage;
import dev.gymService.utills.FileLogger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class TraineeDAO {
    private InMemoryStorage inMemoryStorage;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage){
        this.inMemoryStorage = inMemoryStorage;
    }

    public Trainee getById(Long id) {
        logger.info("Getting trainee with id: " + id);
        return inMemoryStorage.getTraineeStorage().get(id);
    }

    public void create(Trainee trainee) {
        logger.info("Creating new trainee with id: " + trainee.getUserId());
        inMemoryStorage.getTraineeStorage().put(trainee.getUserId(), trainee);
    }
    public void update(Trainee trainee) {
        logger.info("Updating trainee with id: " + trainee.getUserId());
        inMemoryStorage.getTraineeStorage().put(trainee.getUserId(), trainee);
    }
    public void delete(Long id) {
        logger.info("Deleting trainee with id: " + id);
        inMemoryStorage.getTraineeStorage().remove(id);
    }
    public List<Trainee> getAll(){
        logger.info("Getting the list of all trainees");
        return new ArrayList<>(inMemoryStorage.getTraineeStorage().values());
    }

}
