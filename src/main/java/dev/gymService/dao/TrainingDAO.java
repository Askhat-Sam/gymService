package dev.gymService.dao;

import dev.gymService.model.Training;
import dev.gymService.storage.InMemoryStorage;
import dev.gymService.utills.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TrainingDAO {
    private InMemoryStorage inMemoryStorage;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);
    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage){
        this.inMemoryStorage = inMemoryStorage;
    }

    public Training getById(Long id) {
        logger.log(Level.INFO, "The training  with id [" + id + "] has been requested");
        return inMemoryStorage.getTrainingStorage().get(id);
    }

    public Training create(Training training) {
        training.setTrainingId(generateTrainingId());
        logger.log(Level.INFO, "New training with id [" + training.getTrainingId() + "] has been created");
        return inMemoryStorage.getTrainingStorage().put(training.getTrainingId(), training);
    }
    public Training update(Training training) {
        logger.log(Level.INFO, "The training  with id [" + training.getTrainingId() + "] has been updated");
        return inMemoryStorage.getTrainingStorage().put(training.getTrainingId(), training);
    }
    public void delete(Long id) {
        logger.log(Level.INFO, "The trainer  with id [" + id + "] has been deleted");
        inMemoryStorage.getTrainingStorage().remove(id);
    }
    public List<Training> getAll(){
        logger.log(Level.INFO, "The list of trainings has been requested");
        return new ArrayList<>(inMemoryStorage.getTrainingStorage().values());
    }
    public Long generateTrainingId() {
        long maxNumber = getAll().size();
        return maxNumber + 1;
    }
}
