package dev.gymService.repository.implmentations;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TraineeRepository;
import dev.gymService.utills.FileLogger;
import org.hibernate.SessionFactory;
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

    private final SessionFactory sessionFactory;

    public TraineeRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trainee create(Trainee trainee) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(trainee);
            logger.log(Level.INFO, "Trainee has been created: " + trainee.getUserName());
            return trainee;
        });
    }

    @Override
    public Trainee getTraineeById(Long id) {
        return sessionFactory.fromTransaction(session -> session.get(Trainee.class, id));
    }

    @Override
    public Trainee getTraineeByUserName(String userName) {
        return sessionFactory.fromTransaction(session -> session.createQuery("SELECT t FROM Trainee t WHERE t.userName = :userName", Trainee.class)
                .setParameter("userName", userName)
                .uniqueResult());
    }

    @Override
    public void deleteTraineeByUserName(String userName) {
        sessionFactory.inTransaction(session -> {
            session.createMutationQuery("DELETE FROM Trainee WHERE userName = :userName")
                    .setParameter("userName", userName)
                    .executeUpdate();
        });
    }


    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String fromDate, String toDate, String trainerName) {
        return sessionFactory.fromTransaction(session -> session.createQuery(
                        "SELECT t FROM Training t " +
                                "WHERE t.trainee.userName = :traineeName " +
                                "AND t.trainingDate >= :fromDate " +
                                "AND t.trainingDate <= :toDate " +
                                "AND t.trainer.userName = :trainerName", Training.class)
                .setParameter("traineeName", traineeName)
                .setParameter("fromDate", LocalDate.parse(fromDate))
                .setParameter("toDate", LocalDate.parse(toDate))
                .setParameter("trainerName", trainerName)
                .getResultList());
    }


    @Override
    public Trainee updateTrainee(Trainee trainee) {
        return sessionFactory.fromTransaction(session -> {
            session.merge(trainee);
            return trainee;
        });
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName) {
        return sessionFactory.fromTransaction(session -> session.createQuery(
                        "SELECT tr FROM Trainer tr " +
                                "WHERE tr.id NOT IN (" +
                                "   SELECT ttr.id FROM Trainee t " +
                                "   JOIN t.trainers ttr " +
                                "   WHERE t.userName = :traineeUserName)",
                        Trainer.class)
                .setParameter("traineeUserName", traineeUserName)
                .getResultList());
    }
}
