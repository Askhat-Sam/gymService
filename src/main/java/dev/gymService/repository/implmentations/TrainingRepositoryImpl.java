package dev.gymService.repository.implmentations;

import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.repository.interfaces.TrainingRepository;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainingRepositoryImpl implements TrainingRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SELECT_BY_TRAINING_NAME = "SELECT t FROM TrainingType t WHERE t.trainingTypeName = :trainingName";
    private static final String SELECT_TRAINING_TYPES = "SELECT  t FROM TrainingType t";
    private final SessionFactory sessionFactory;

    public TrainingRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Training addTraining(Training training) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(training);
            logger.info("Training has been added: " + training.getTrainingId());
            return training;
        });
    }

    @Override
    public TrainingType getTrainingTypeIdByTrainingName(String trainingName) {
        return sessionFactory.fromSession(session -> session.createQuery(SELECT_BY_TRAINING_NAME, TrainingType.class)
                .setParameter("trainingName", trainingName)
                .uniqueResult());
    }

    @Override
    public List<TrainingType> getTrainingTypes() {
        return sessionFactory.fromSession(session -> session.createQuery(SELECT_TRAINING_TYPES, TrainingType.class)
                .getResultList());
    }

}
