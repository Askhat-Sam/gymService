package dev.gymService.dao;

import dev.gymService.model.Trainee;
import dev.gymService.model.User;
import dev.gymService.storage.InMemoryStorage;
import dev.gymService.utills.UserInformationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Component
public class TraineeDAO {

    private InMemoryStorage inMemoryStorage;

    @Autowired
    public void setInMemoryStorage(InMemoryStorage inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    public Trainee getById(Long id) {
        return inMemoryStorage.getTraineeStorage().get(id);
    }

    public Trainee create(Trainee trainee) {
        trainee.setUserId(generateTraineeId());
        trainee.setUserName(UserInformationUtility.generateUserName(trainee.getFirstName(), trainee.getLastName(),
                this.getAll().stream().map(User::getUserName).collect(Collectors.toList())));
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

    public List<Trainee> getAll() {
        return new ArrayList<>(inMemoryStorage.getTraineeStorage().values());
    }

    public long generateTraineeId() {
        long maxNumber = getAll().size();
        return maxNumber + 1;
    }

//    public String generateUserName(String firstName, String lastName){
//        long userNameSuffix = 1;
//        String userName = firstName.concat(".").concat(lastName);
//        String originalUserName = userName;
//        // Check if the userName is unique
//        while (checkUserName(userName)) {
//            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
//        }
//
//        return userName;
//    }
//
//    public boolean checkUserName(String userName) {
//        return this.getAll().stream().anyMatch(t -> t.getUserName().equals(userName));
//    }

}
