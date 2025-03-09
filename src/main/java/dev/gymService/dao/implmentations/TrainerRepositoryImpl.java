package dev.gymService.dao.implmentations;

import dev.gymService.dao.interfaces.TrainerRepository;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.utills.FileLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
    private static final Logger logger = FileLogger.getLogger(TrainerRepositoryImpl.class);
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
    public Trainer getTrainerById(Long id, String userName, String password) {
        try (Session session = sessionFactory.openSession()) {
            //  Check userName and password matching
            Trainer trainer = session.get(Trainer.class, id);
            if (trainer.getUserName().equals(userName) && trainer.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainer.getUserName());
                return trainer;
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainer: " + trainer.getUserName());
                return null;
            }
        }
    }

    @Override
    public Trainer getTrainerByUserName(String userName, String password) {
        try (Session session = sessionFactory.openSession()) {
            //  Check userName and password matching
            Trainer trainer = session.createQuery("SELECT t FROM Trainer t WHERE t.user.userName = :userName", Trainer.class)
                    .setParameter("userName", userName)
                    .uniqueResult();
            if (trainer.getUserName().equals(userName) && trainer.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainer.getUserName());
                return trainer;
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainer: " + trainer.getUserName());
                return null;
            }
        }
    }

    @Override
    public void changeTrainerPassword(String userName, String oldPassword, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            //  Check userName and password matching
            Trainer trainer = this.getTrainerByUserName(userName, oldPassword);
            if (trainer.getUserName().equals(userName) && trainer.getPassword().equals(oldPassword)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainer.getUserName());
                session.createQuery("UPDATE User u SET u.password = :newPassword WHERE u.userName = :userName")
                        .setParameter("newPassword", newPassword)
                        .setParameter("userName", userName)
                        .executeUpdate();
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainer: " + trainer.getUserName());
            }
            transaction.commit();
        }
    }

    @Override
    public Trainer updateTrainee(Trainer trainer) {
        Trainer existingTrainer = getTrainerByUserName(trainer.getUserName(), trainer.getPassword());
        if (existingTrainer.getUserName().equals(trainer.getUserName()) && existingTrainer.getPassword().equals(trainer.getPassword())) {
            existingTrainer.setFirstName(trainer.getFirstName());
            existingTrainer.setLastName(trainer.getLastName());
            existingTrainer.setPassword(trainer.getPassword());
            existingTrainer.setIsActive(trainer.getIsActive());
            existingTrainer.setSpecialization(trainer.getSpecialization());

            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainer.getUserName());
                Trainer updatedTrainer = (Trainer) session.merge(existingTrainer);
                transaction.commit();
                return updatedTrainer;

            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + trainer.getUserName());
            return null;
        }
    }

    @Override
    public void changeTrainerStatus(String userName, String password) {
        Transaction transaction = null;
        Trainer trainer = getTrainerByUserName(userName, password);
        if (trainer.getUserName().equals(userName) && trainer.getPassword().equals(password)) {
            try (Session session = sessionFactory.openSession()) {
                transaction = session.beginTransaction();
                // Toggle status
                Boolean isActive = !trainer.getIsActive();

                // Update trainee's active status
                session.createQuery("UPDATE Trainer t SET t.isActive = :isActive WHERE t.userName = :userName")
                        .setParameter("isActive", isActive)
                        .setParameter("userName", userName)
                        .executeUpdate();

                transaction.commit();
                System.out.println("Trainer status updated successfully.");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + trainer.getUserName());
        }
    }

        @Override
        public List<Training> getTrainerTrainingList (String trainerName, String password, String fromDate, String toDate, String
        traineeName){
            Trainer trainer = this.getTrainerByUserName(trainerName, password);
            if (trainer.getUserName().equals(trainerName) && trainer.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainer.getUserName());
                try (Session session = sessionFactory.openSession()) {
                    return session.createQuery(
                                    "SELECT t FROM Training t " +
                                            "WHERE t.trainer.userName = :trainerName " +
                                            "AND t.trainingDate >= :fromDate " +
                                            "AND t.trainingDate <= :toDate " +
                                            "AND t.trainee.userName = :traineeName", Training.class)
                            .setParameter("trainerName", trainerName)
                            .setParameter("fromDate", LocalDate.parse(fromDate))
                            .setParameter("toDate", LocalDate.parse(toDate))
                            .setParameter("traineeName", traineeName)
                            .getResultList();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Exception has been thrown: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainer: " + trainer.getUserName());
                return null;
            }
        }
    }
