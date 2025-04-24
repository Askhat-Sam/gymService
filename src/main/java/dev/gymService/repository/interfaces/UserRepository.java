package dev.gymService.repository.interfaces;

import dev.gymService.model.User;

public interface UserRepository<T extends User> {
    T create(User user);

    T update(User user);

    T getById(Long id);

    T getByUserName(String userName);

    void deleteByUserName(String userName);
}
