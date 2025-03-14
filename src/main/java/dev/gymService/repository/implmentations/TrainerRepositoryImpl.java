package dev.gymService.repository.implmentations;

import dev.gymService.model.Trainee;
import dev.gymService.repository.interfaces.TrainerRepository;
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
    private final SessionFactory sessionFactory;

    public TrainerRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trainer create(Trainer trainer) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(trainer);
            logger.log(Level.INFO, "Trainer has been created: " + trainer.getUserName());
            return trainer;
        });
    }

    @Override
    public Trainer getTrainerById(Long id) {
        return sessionFactory.fromTransaction(session -> session.get(Trainer.class, id));
    }

    @Override
    public Trainer getTrainerByUserName(String userName) {
        return sessionFactory.fromTransaction(session -> session.createQuery("SELECT t FROM Trainer t WHERE t.userName = :userName", Trainer.class)
                .setParameter("userName", userName)
                .uniqueResult());
    }


    @Override
    public Trainer updateTrainer(Trainer trainer) {
        return sessionFactory.fromTransaction(session -> {
            session.merge(trainer);
            return trainer;
        });
    }


    @Override
    public List<Training> getTrainerTrainingList(String trainerName, String fromDate, String toDate, String
            traineeName) {
        return sessionFactory.fromTransaction(session -> session.createQuery(
                        "SELECT t FROM Training t " +
                                "WHERE t.trainer.userName = :trainerName " +
                                "AND t.trainingDate >= :fromDate " +
                                "AND t.trainingDate <= :toDate " +
                                "AND t.trainee.userName = :traineeName", Training.class)
                .setParameter("trainerName", trainerName)
                .setParameter("fromDate", LocalDate.parse(fromDate))
                .setParameter("toDate", LocalDate.parse(toDate))
                .setParameter("traineeName", traineeName)
                .getResultList());
    }
}
