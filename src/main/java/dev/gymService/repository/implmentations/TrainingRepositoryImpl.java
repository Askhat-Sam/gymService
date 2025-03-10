package dev.gymService.repository.implmentations;

import dev.gymService.repository.interfaces.TrainingRepository;
import dev.gymService.model.Training;
import dev.gymService.utills.FileLogger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.logging.Level;
import java.util.logging.Logger;

@Repository
public class TrainingRepositoryImpl implements TrainingRepository {
    private static final Logger logger = FileLogger.getLogger(TrainingRepositoryImpl.class);
    @Autowired
    @Qualifier("getSessionFactory")
    SessionFactory sessionFactory;
    @Override
    public Training addTraining(Training training) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(training);
        transaction.commit();
        logger.log(Level.INFO, "Training has been added: " + training.getTrainingId());
        session.close();
        return training;
    }
}
