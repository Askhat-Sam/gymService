package dev.gymService.dao;

import dev.gymService.model.Training;
import dev.gymService.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainingDAO {
    private InMemoryStorage inMemoryStorage;
    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage){
        this.inMemoryStorage = inMemoryStorage;
    }

    public Training getById(Long id) {
        return inMemoryStorage.getTrainingStorage().get(id);
    }

    public Training create(Training training) {
        return inMemoryStorage.getTrainingStorage().put(training.getTrainerId(), training);
    }
    public Training update(Training training) {
        return inMemoryStorage.getTrainingStorage().put(training.getTrainerId(), training);
    }
    public void delete(Long id) {
        inMemoryStorage.getTrainingStorage().remove(id);
    }
    public List<Training> getAll(){
        return new ArrayList<>(inMemoryStorage.getTrainingStorage().values());
    }
    public Long generateTrainingId() {
        long maxNumber = getAll().size();
        return ++maxNumber;
    }
}
