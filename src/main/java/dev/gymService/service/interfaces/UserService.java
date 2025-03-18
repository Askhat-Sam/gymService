package dev.gymService.service.interfaces;

import dev.gymService.model.User;
import dev.gymService.repository.implmentations.TraineeRepositoryImpl;
import dev.gymService.utills.FileLogger;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface UserService {
    static final Logger logger = FileLogger.getLogger(TraineeRepositoryImpl.class);
    default boolean isAuthenticated(String userName, String password, User user) {
        if (user != null && user.getUserName().equals(userName) && user.getPassword().equals(password)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + userName);
            return true;
        } else {
            logger.log(Level.INFO, "Failed authentication for trainee: " + userName);
            throw new RuntimeException("Failed authentication for trainee: " + userName);
        }
    }
}
