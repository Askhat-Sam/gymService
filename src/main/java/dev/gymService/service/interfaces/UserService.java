package dev.gymService.service.interfaces;

import dev.gymService.model.User;

public interface UserService {
    User findByUserName(String userName);
}
