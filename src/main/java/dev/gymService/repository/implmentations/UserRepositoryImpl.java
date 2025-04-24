package dev.gymService.repository.implmentations;

import dev.gymService.model.Trainee;
import dev.gymService.model.User;
import dev.gymService.repository.interfaces.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class UserRepositoryImpl implements UserRepository<User> {

    private final SessionFactory sessionFactory;
    private static final String SELECT_BY_USERNAME = "SELECT u FROM User u WHERE u.userName = :userName";


    public UserRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User getByUserName(String userName) {
        return sessionFactory.fromTransaction(session -> session.createQuery(SELECT_BY_USERNAME, User.class)
                .setParameter("userName", userName)
                .uniqueResult());
    }

    @Override
    public void deleteByUserName(String userName) {

    }
}
