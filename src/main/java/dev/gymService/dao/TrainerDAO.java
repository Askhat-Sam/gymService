package dev.gymService.dao;

import dev.gymService.model.Trainer;
import dev.gymService.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TrainerDAO {
    private InMemoryStorage inMemoryStorage;
    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage){
        this.inMemoryStorage = inMemoryStorage;
    }

    public Trainer getById(Long id) {
        return inMemoryStorage.getTrainerStorage().get(id);
    }

    public Trainer create(Trainer trainer) {
        return inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
    }
    public Trainer update(Trainer trainer) {
        return inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
    }
    public void delete(Long id) {
        inMemoryStorage.getTrainerStorage().remove(id);
    }
    public List<Trainer> getAll(){
        return new ArrayList<>(inMemoryStorage.getTrainerStorage().values());
    }

    public Long generateTrainerId() {
        long maxNumber = getAll().size();
        return ++maxNumber;
    }
}
