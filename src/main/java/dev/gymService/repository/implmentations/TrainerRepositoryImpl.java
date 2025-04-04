package dev.gymService.repository.implmentations;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TrainerRepository;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TrainerRepositoryImpl implements TrainerRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final SessionFactory sessionFactory;

    private static final String SELECT_BY_USERNAME = "SELECT t FROM Trainer t WHERE t.userName = :userName";
    private static final String GET_TRAINING_LIST = "SELECT t FROM Training t " +
            "WHERE t.trainer.userName = :trainerName " +
            "AND t.trainingDate >= :fromDate " +
            "AND t.trainingDate <= :toDate ";

    public TrainerRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trainer create(Trainer trainer) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(trainer);
            logger.info("Trainer has been created: " + trainer.getUserName());
            return trainer;
        });
    }

    @Override
    public Trainer getTrainerById(Long id) {
        return sessionFactory.fromTransaction(session -> session.get(Trainer.class, id));
    }

    @Override
    public Trainer getTrainerByUserName(String userName) {
        return sessionFactory.fromTransaction(session -> session.createQuery(SELECT_BY_USERNAME, Trainer.class)
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
            traineeName, Long trainingTypeId) {
        StringBuilder query = new StringBuilder(GET_TRAINING_LIST);
        if (traineeName != null && !traineeName.isEmpty()) {
            query.append("AND t.trainee.userName = :traineeName ");
        }
        if (trainingTypeId != null && trainingTypeId != 0) {
            query.append("AND t.trainingTypeId = :trainingTypeId ");
        }

        var queryObj = sessionFactory.fromTransaction(session -> {
            var queryResult = session.createQuery(query.toString(), Training.class)
                    .setParameter("trainerName", trainerName)
                    .setParameter("fromDate", LocalDate.parse(fromDate))
                    .setParameter("toDate", LocalDate.parse(toDate));

            if (traineeName != null && !traineeName.isEmpty()) {
                queryResult.setParameter("traineeName", traineeName);
            }
            if (trainingTypeId != null && trainingTypeId != 0) {
                queryResult.setParameter("trainingTypeId", trainingTypeId);
            }

            return queryResult.getResultList();
        });

        return queryObj;
    }
}
