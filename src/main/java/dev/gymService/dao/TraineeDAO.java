//package dev.gymService.dao;
//
//import dev.gymService.model.Trainee;
//import dev.gymService.storage.InMemoryStorage;
//import dev.gymService.utills.FileLogger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Component
//public class TraineeDAO extends Dao<Trainee>{
//
//    private InMemoryStorage inMemoryStorage;
//    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);
//
//    @Autowired
//    public void setInMemoryStorage(InMemoryStorage inMemoryStorage) {
//        this.inMemoryStorage = inMemoryStorage;
//    }
//    public Trainee getById(Long id) {
//        logger.log(Level.INFO, "The trainee  with id [" + id + "] has been requested");
//        return inMemoryStorage.getTraineeStorage().get(id);
//    }
//
//    public Trainee create(Trainee trainee) {
//        trainee.setUserId(generateTraineeId());
//        inMemoryStorage.getTraineeStorage().put(trainee.getUserId(), trainee);
//        logger.log(Level.INFO, "New trainee  with id [" + trainee.getUserId() + "] has been created");
//        return inMemoryStorage.getTraineeStorage().get(trainee.getUserId());
//    }
//
//    public Trainee update(Trainee trainee) {
//        inMemoryStorage.getTraineeStorage().put(trainee.getUserId(), trainee);
//        logger.log(Level.INFO, "The trainee  with id [" + trainee.getUserId() + "] has been updated");
//        return inMemoryStorage.getTraineeStorage().get(trainee.getUserId());
//    }
//
//    public void delete(Long id) {
//        inMemoryStorage.getTraineeStorage().remove(id);
//        logger.log(Level.INFO, "The trainee  with id [" + id + "] has been deleted");
//    }
//
//    public List<Trainee> getAll() {
//        logger.log(Level.INFO, "The list of trainees has been requested");
//        return new ArrayList<>(inMemoryStorage.getTraineeStorage().values());
//    }
//
//    public Long generateTraineeId() {
//        return generateId(Trainee::getUserId);
//    }
//
//    public Trainee getTraineeByUserName(String userName) {
//        return inMemoryStorage.getTraineeStorage().values()
//                .stream()
//                .filter(trainee -> userName.equals(trainee.getUserName()))
//                .findFirst()
//                .orElse(null);
//    }
//
//}
