package dev.gymService.dao;

import dev.gymService.model.Trainee;
import dev.gymService.storage.InMemoryStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TraineeDAO {

    private InMemoryStorage inMemoryStorage;

    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage){
        this.inMemoryStorage = inMemoryStorage;
    }

    public Trainee getById(Long id) {
        return inMemoryStorage.getTraineeStorage().get(id);
    }

    public Trainee create(Trainee trainee) {
        inMemoryStorage.getTraineeStorage().put(trainee.getUserId(), trainee);
        return inMemoryStorage.getTraineeStorage().get(trainee.getUserId());
    }
    public Trainee update(Trainee trainee) {
        inMemoryStorage.getTraineeStorage().put(trainee.getUserId(), trainee);
        return inMemoryStorage.getTraineeStorage().get(trainee.getUserId());
    }
    public void delete(Long id) {
        inMemoryStorage.getTraineeStorage().remove(id);
    }
    public List<Trainee> getAll(){
        return new ArrayList<>(inMemoryStorage.getTraineeStorage().values());
    }

    public long generateTraineeId() {
        long maxNumber = getAll().size();
        return ++maxNumber;
    }
}
