package dev.gymService.dao.implmentations;

import dev.gymService.dao.interfaces.TraineeRepository;
import dev.gymService.dao.interfaces.TrainerRepository;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
    @Autowired
    @Qualifier("getSessionFactory")
    SessionFactory sessionFactory;

    @Override
    public Trainer create(Trainer trainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(trainer);
        transaction.commit();
        session.close();
        return trainer;
    }

    @Override
    public Trainer getTrainerById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Trainer.class, id);
        }
    }

    @Override
    public Trainer getTrainerByUserName(String userName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery( "SELECT t FROM Trainer t WHERE t.user.userName = :userName", Trainer.class)
                    .setParameter("userName", userName)
                    .uniqueResult();
        }
    }

    @Override
    public void changeTrainerPassword(String userName,String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.createQuery( "UPDATE User u SET u.password = :newPassword WHERE u.userName = :userName")
                    .setParameter("newPassword", newPassword)
                    .setParameter("userName", userName)
                    .executeUpdate();

            transaction.commit();
        }
    }

    @Override
    public Trainer updateTrainee(Trainer trainer) {
        Trainer existingTrainer = getTrainerByUserName(trainer.getUserName());

        existingTrainer.setFirstName(trainer.getFirstName());
        existingTrainer.setLastName(trainer.getLastName());
        existingTrainer.setPassword(trainer.getPassword());
        existingTrainer.setIsActive(trainer.getIsActive());
        existingTrainer.setSpecialization(trainer.getSpecialization());

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Trainer updatedTrainer = (Trainer) session.merge(existingTrainer);
            transaction.commit();
            return updatedTrainer;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void changeTrainerStatus(String userName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Trainer trainer = session.createQuery( "SELECT t FROM Trainer t WHERE t.userName = :userName", Trainer.class)
                    .setParameter("userName", userName)
                    .uniqueResult();

            if (trainer != null) {
                Boolean isActive = !trainer.getIsActive(); // Toggle status

                // Update trainee's active status
                session.createQuery("UPDATE Trainer t SET t.isActive = :isActive WHERE t.userName = :userName")
                        .setParameter("isActive", isActive)
                        .setParameter("userName", userName)
                        .executeUpdate();

                transaction.commit();
                System.out.println("Trainer status updated successfully.");
            } else {
                System.out.println("Trainer not found with username: " + userName);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<Trainer> findAll() {
        return null;
    }

    @Override
    public Trainer update(Trainer trainer) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
