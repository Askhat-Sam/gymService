package dev.gymService.dao.implmentations;

import dev.gymService.dao.interfaces.TraineeRepository;
import dev.gymService.model.Trainee;
import dev.gymService.model.Training;
import org.hibernate.Session;
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
    public void changeTraineePassword(String userName,String newPassword) {
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
    public void deleteTraineeByUserName(String userName) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM Trainee WHERE userName = :userName")
                    .setParameter("userName", userName)
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void changeTraineeStatus(String userName) {
        // Get trainee by userName
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Trainee trainee = session.createQuery( "SELECT t FROM Trainee t WHERE t.userName = :userName", Trainee.class)
                    .setParameter("userName", userName)
                    .uniqueResult();

            if (trainee != null) {
                Boolean isActive = !trainee.getIsActive(); // Toggle status

                // Update trainee's active status
                session.createQuery("UPDATE Trainee t SET t.isActive = :isActive WHERE t.userName = :userName")
                        .setParameter("isActive", isActive)
                        .setParameter("userName", userName)
                        .executeUpdate();

                transaction.commit();
                System.out.println("Trainee status updated successfully.");
            } else {
                System.out.println("Trainee not found with username: " + userName);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<Training> getTraineeTrainings(String userName, String fromDate, String toDate, String trainerUserName) {
        return null;
    }

    @Override
    public Trainee updateTrainee(Trainee trainee) {
        Trainee existingTrainee = getTraineeByUserName(trainee.getUserName());

        existingTrainee.setFirstName(trainee.getFirstName());
        existingTrainee.setLastName(trainee.getLastName());
        existingTrainee.setPassword(trainee.getPassword());
        existingTrainee.setIsActive(trainee.getIsActive());
        existingTrainee.setDateOfBirth(trainee.getDateOfBirth());
        existingTrainee.setAddress(trainee.getAddress());

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Trainee updatedTrainee = (Trainee) session.merge(existingTrainee);
            transaction.commit();
            return updatedTrainee;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<Trainee> findAll() {
        return null;
    }



    @Override
    public void delete(Long id) {

    }
}
