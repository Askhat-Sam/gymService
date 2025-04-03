package dev.gymService.service.interfaces;

import dev.gymService.exception.UserNotAuthenticatedException;
import dev.gymService.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface UserService {
    final Logger logger = LoggerFactory.getLogger("UserService");

    default boolean isAuthenticated(String userName, String password, User user) {
        if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
            logger.info("Successful authentication for trainee: " + userName);
            return true;
        } else {
            logger.info("Failed authentication for trainee: " + userName);
            throw new UserNotAuthenticatedException("Failed to authenticate the user: [" + userName + "]");
        }
    }
}