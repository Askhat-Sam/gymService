package dev.gymService.dao.implmentations;

import dev.gymService.dao.interfaces.TraineeRepository;
import dev.gymService.model.Trainee;
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
public class TraineeRepositoryImpl implements TraineeRepository {
    private static final Logger logger = FileLogger.getLogger(TraineeRepositoryImpl.class);
    @Autowired
    @Qualifier("getSessionFactory")
    SessionFactory sessionFactory;

    @Override
    public Trainee create(Trainee trainee) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            // Required fields validation
            if (trainee.getFirstName() != null && trainee.getLastName() != null && trainee.getUserName() != null
                    && trainee.getPassword() != null && trainee.getIsActive() != null) {
                session.persist(trainee);
                transaction.commit();
                logger.log(Level.INFO, "Trainee has been created: " + trainee.getUserName());
            } else {
                logger.log(Level.INFO, "Required fields validation has not been passed: " + trainee.getUserName());
            }
            return trainee;
        }
    }

    @Override
    public Trainee getTraineeById(Long id, String userName, String password) {
        try (Session session = sessionFactory.openSession()) {
            Trainee trainee = session.get(Trainee.class, id);
            //  Check userName and password for matching
            if (trainee.getUserName().equals(userName) && trainee.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successful authentication for trainer: " + trainee.getUserName());
                return trainee;
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
                return null;
            }
        }
    }

    @Override
    public Trainee getTraineeByUserName(String userName, String password) {
        try (Session session = sessionFactory.openSession()) {
            //  Check userName and password matching
            Trainee trainee = session.createQuery("SELECT t FROM Trainee t WHERE t.userName = :userName", Trainee.class)
                    .setParameter("userName", userName)
                    .uniqueResult();
            if (trainee.getUserName().equals(userName) && trainee.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainee.getUserName());
                return trainee;
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
                return null;
            }
        }
    }

    @Override
    public void changeTraineePassword(String userName, String oldPassword, String newPassword) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            //  Check userName and password matching
            Trainee trainee = this.getTraineeByUserName(userName, oldPassword);
            if (trainee.getUserName().equals(userName) && trainee.getPassword().equals(oldPassword)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainee.getUserName());
                session.createQuery("UPDATE User u SET u.password = :newPassword WHERE u.userName = :userName")
                        .setParameter("newPassword", newPassword)
                        .setParameter("userName", userName)
                        .executeUpdate();
                logger.log(Level.INFO, "Trainee's password has been updated: " + trainee.getUserName());
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
            }
            transaction.commit();
        }
    }

    @Override
    public void deleteTraineeByUserName(String userName, String password) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            //  Check userName and password matching
            Trainee trainee = this.getTraineeByUserName(userName, password);
            if (trainee.getUserName().equals(userName) && trainee.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainee.getUserName());
                session.createQuery("DELETE FROM Trainee WHERE userName = :userName")
                        .setParameter("userName", userName)
                        .executeUpdate();
                logger.log(Level.INFO, "Trainee has been deleted: " + trainee.getUserName());
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
            }
            transaction.commit();
        }
    }

    @Override
    public void changeTraineeStatus(String userName, String password) {
        // Get trainee by userName
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Trainee trainee = this.getTraineeByUserName(userName, password);

            if (trainee.getUserName().equals(userName) && trainee.getPassword().equals(password)) {
                Boolean isActive = !trainee.getIsActive(); // Toggle status

                // Update trainee's active status
                session.createQuery("UPDATE Trainee t SET t.isActive = :isActive WHERE t.userName = :userName")
                        .setParameter("isActive", isActive)
                        .setParameter("userName", userName)
                        .executeUpdate();

                transaction.commit();
                System.out.println("Trainee status updated successfully.");
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
            }
        }
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName) {
        try (Session session = sessionFactory.openSession()) {
            //  Check userName and password matching
            Trainee trainee = this.getTraineeByUserName(traineeName, password);
            if (trainee.getUserName().equals(traineeName) && trainee.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainee.getUserName());
                return session.createQuery(
                                "SELECT t FROM Training t " +
                                        "WHERE t.trainee.userName = :traineeName " +
                                        "AND t.trainingDate >= :fromDate " +
                                        "AND t.trainingDate <= :toDate " +
                                        "AND t.trainer.userName = :trainerName", Training.class)
                        .setParameter("traineeName", traineeName)
                        .setParameter("fromDate", LocalDate.parse(fromDate))
                        .setParameter("toDate", LocalDate.parse(toDate))
                        .setParameter("trainerName", trainerName)
                        .getResultList();
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
                return null;
            }
        }
    }


    @Override
    public Trainee updateTrainee(Trainee trainee) {
        Trainee existingTrainee = getTraineeByUserName(trainee.getUserName(), trainee.getPassword());
        //  Check userName and password matching
        if (existingTrainee.getUserName().equals(trainee.getUserName()) && existingTrainee.getPassword().equals(trainee.getPassword())) {
            logger.log(Level.INFO, "Successfull authentification for trainer: " + trainee.getUserName());
            existingTrainee.setFirstName(trainee.getFirstName());
            existingTrainee.setLastName(trainee.getLastName());
            existingTrainee.setPassword(trainee.getPassword());
            existingTrainee.setIsActive(trainee.getIsActive());
            existingTrainee.setDateOfBirth(trainee.getDateOfBirth());
            existingTrainee.setAddress(trainee.getAddress());

            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                // Required fields validation
                if (trainee.getFirstName() != null && trainee.getLastName() != null && trainee.getUserName() != null
                        && trainee.getPassword() != null && trainee.getIsActive() != null) {
                    Trainee updatedTrainee = (Trainee) session.merge(existingTrainee);
                    transaction.commit();
                    logger.log(Level.INFO, "Trainee has been updated: " + trainee.getUserName());
                    return updatedTrainee;
                } else {
                    logger.log(Level.INFO, "Required fields validation has not been passed: " + trainee.getUserName());
                }
            }
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
            return null;
        }
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName, String password) {
        try (Session session = sessionFactory.openSession()) {
            //  Check userName and password matching
            Trainee trainee = this.getTraineeByUserName(traineeUserName, password);
            if (trainee.getUserName().equals(traineeUserName) && trainee.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainee.getUserName());
                return session.createQuery(
                                "SELECT tr FROM Trainer tr " +
                                        "WHERE tr.id NOT IN (" +
                                        "   SELECT ttr.id FROM Trainee t " +
                                        "   JOIN t.trainers ttr " +
                                        "   WHERE t.user.userName = :traineeUserName" +
                                        ")",
                                Trainer.class)
                        .setParameter("traineeUserName", traineeUserName)
                        .getResultList();
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
                return null;
            }
        }
    }

    @Override
    public void updateTrainersList(String traineeUserName, String password, List<Trainer> trainers) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            //  Check userName and password matching
            Trainee trainee = this.getTraineeByUserName(traineeUserName, password);
            if (trainee.getUserName().equals(traineeUserName) && trainee.getPassword().equals(password)) {
                logger.log(Level.INFO, "Successfull authentification for trainer: " + trainee.getUserName());

                trainee.setTrainers(trainers);
                session.update(trainee);
                logger.log(Level.INFO, "Trainee's trainers list has been updated: " + trainee.getUserName());
            } else {
                logger.log(Level.INFO, "Incorrect userName and password for trainee: " + trainee.getUserName());
            }
            transaction.commit();
        }
    }
}
