package dev.gymService.service.interfaces;

import dev.gymService.model.User;

public interface UserService {
    default boolean isAuthenticated(String userName, String password, User user) {
        return user != null && user.getUserName().equals(userName) && user.getPassword().equals(password);
    }
}
