package dev.gymService.repository.implmentations;

import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TrainingRepository;
import dev.gymService.utills.FileLogger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TrainingRepositoryImpl implements TrainingRepository {
    private static final Logger logger = FileLogger.getLogger(TrainingRepositoryImpl.class);
    private final SessionFactory sessionFactory;

    public TrainingRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Training addTraining(Training training) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(training);
            logger.log(Level.INFO, "Training has been added: " + training.getTrainingId());
            return training;
        });
    }
}
