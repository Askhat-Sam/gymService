package dev.gymService.service.implementations;

import dev.gymService.model.User;
import dev.gymService.repository.interfaces.UserRepository;
import dev.gymService.service.interfaces.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }
}
