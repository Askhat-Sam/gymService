package dev.gymService.dao;

import dev.gymService.model.Training;
import dev.gymService.storage.InMemoryStorage;
import dev.gymService.utills.FileLogger;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Repository
public class TrainingDAO {
    private final InMemoryStorage inMemoryStorage;
    private static final Logger logger = FileLogger.getLogger(TrainingDAO.class);

    public TrainingDAO(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    public Training getById(Long id) {
        logger.info("Getting training with id: " + id);
        return inMemoryStorage.getTrainingStorage().get(id);
    }

    public void create(Training training) {
        logger.info("Creating new trainer with id: " + training.getTrainerId());
        inMemoryStorage.getTrainingStorage().put(training.getTrainerId(), training);
    }
    public void update(Training training) {
        logger.info("Updating training with id: " +training.getTrainerId());
        inMemoryStorage.getTrainingStorage().put(training.getTrainerId(), training);
    }
    public void delete(Long id) {
        logger.info("Deleting training with id: " + id);
        inMemoryStorage.getTrainingStorage().remove(id);
    }
    public List<Training> getAll(){
        logger.info("Getting the list of all trainings");
        return new ArrayList<>(inMemoryStorage.getTrainingStorage().values());
    }
}
