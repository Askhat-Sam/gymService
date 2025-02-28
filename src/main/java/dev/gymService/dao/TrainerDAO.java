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
        trainer.setUserId(generateTrainerId());
        trainer.setUserName(generateUserName(trainer.getFirstName(), trainer.getLastName()));
        inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
        return inMemoryStorage.getTrainerStorage().get(trainer.getUserId());
    }
    public Trainer update(Trainer trainer) {
        inMemoryStorage.getTrainerStorage().put(trainer.getUserId(), trainer);
        return inMemoryStorage.getTrainerStorage().get(trainer.getUserId());
    }
    public void delete(Long id) {
        inMemoryStorage.getTrainerStorage().remove(id);
    }
    public List<Trainer> getAll(){
        return new ArrayList<>(inMemoryStorage.getTrainerStorage().values());
    }

    public Long generateTrainerId() {
        long maxNumber = getAll().size();
        return maxNumber + 1;
    }

    public String generateUserName(String firstName, String lastName){
        long userNameSuffix = 1;
        String userName = firstName.concat(".").concat(lastName);
        String originalUserName = userName;
        // Check if the userName is unique
        while (checkUserName(userName)) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }

        return userName;
    }

    public boolean checkUserName(String userName) {
        return this.getAll().stream().anyMatch(t -> t.getUserName().equals(userName));
    }
}
