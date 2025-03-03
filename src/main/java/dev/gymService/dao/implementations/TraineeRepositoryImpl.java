package dev.gymService.dao.implementations;


import org.hibernate.Session;
import dev.gymService.dao.interfaces.TraineeRepository;
import dev.gymService.model.Trainee;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class TraineeRepositoryImpl implements TraineeRepository {
    @Autowired
    @Qualifier("getSessionFactory")
    SessionFactory sessionFactory;


    @Override
    public Trainee create(Trainee trainee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(trainee);
        transaction.commit();
        session.close();
        return trainee;
    }

    @Override
    public Trainee getTraineeById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Trainee.class, id);
        }
    }

    @Override
    public Trainee getTraineeByUserName(String userName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery( "SELECT t FROM Trainee t WHERE t.user.userName = :userName", Trainee.class)
                    .setParameter("userName", userName)
                    .uniqueResult();
        }
    }

    @Override
    public List<Trainee> findAll() {
        return null;
    }

    @Override
    public Trainee update(Trainee trainee) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
