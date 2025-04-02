package dev.gymService.service.interfaces;

import dev.gymService.exception.UserNotAuthenticatedException;
import dev.gymService.model.User;

public interface UserService {
//    static final Logger logger = FileLogger.getLogger(TraineeRepositoryImpl.class);

    default boolean isAuthenticated(String userName, String password, User user) {
        if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
//            logger.log(Level.INFO, "Successful authentication for trainee: " + userName);
            return true;
        } else {
//            logger.log(Level.INFO, "Failed authentication for trainee: " + userName);
            throw new UserNotAuthenticatedException("Failed to authenticate the user: [" + userName + "]");
        }
    }
}
