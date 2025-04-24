package dev.gymService.repository.implmentations;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.User;
import dev.gymService.repository.interfaces.TraineeRepository;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TraineeRepositoryImpl implements TraineeRepository {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SELECT_BY_USERNAME = "SELECT t FROM Trainee t WHERE t.userName = :userName";
    private static final String DELETE_BY_USERNAME = "DELETE FROM Trainee WHERE userName = :userName";
    private static final String GET_TRAINEE_TRAINING_LIST = "SELECT t FROM Training t " +
            "WHERE t.trainee.userName = :traineeName " +
            "AND t.trainingDate >= :fromDate " +
            "AND t.trainingDate <= :toDate ";

    private static final String GET_NOT_ASSIGNED_TRAINERS = "SELECT tr FROM Trainer tr " +
            "WHERE tr.id NOT IN (" +
            "   SELECT ttr.id FROM Trainee t " +
            "   JOIN t.trainers ttr " +
            "   WHERE t.userName = :traineeUserName)";

    private final SessionFactory sessionFactory;

    public TraineeRepositoryImpl(@Qualifier("getSessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trainee create(User trainee) {
        return sessionFactory.fromTransaction(session -> {
            session.persist(trainee);
            logger.info("Trainee has been created: " + trainee.getUserName());
            return (Trainee) trainee;
        });
    }

    @Override
    public Trainee getById(Long id) {
        return sessionFactory.fromTransaction(session -> session.get(Trainee.class, id));
    }

    @Override
    public Trainee getByUserName(String userName) {
        return sessionFactory.fromTransaction(session -> session.createQuery(SELECT_BY_USERNAME, Trainee.class)
                .setParameter("userName", userName)
                .uniqueResult());
    }

    @Override
    public void deleteByUserName(String userName) {
        sessionFactory.inTransaction(session -> {
            session.createMutationQuery(DELETE_BY_USERNAME)
                    .setParameter("userName", userName)
                    .executeUpdate();
        });
    }


    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String fromDate, String toDate, String trainerName, Long trainingTypeId) {
        StringBuilder query = new StringBuilder(GET_TRAINEE_TRAINING_LIST);
        if (trainerName != null && !trainerName.isEmpty()) {
            query.append("AND t.trainer.userName = :trainerName ");
        }
        if (trainingTypeId != null && trainingTypeId != 0) {
            query.append("AND t.trainingTypeId = :trainingTypeId ");
        }

        var queryObj = sessionFactory.fromTransaction(session -> {
            var queryResult = session.createQuery(query.toString(), Training.class)
                    .setParameter("traineeName", traineeName)
                    .setParameter("fromDate", LocalDate.parse(fromDate))
                    .setParameter("toDate", LocalDate.parse(toDate));

            if (trainerName != null && !trainerName.isEmpty()) {
                queryResult.setParameter("trainerName", trainerName);
            }
            if (trainingTypeId != null && trainingTypeId != 0) {
                queryResult.setParameter("trainingTypeId", trainingTypeId);
            }

            return queryResult.getResultList();
        });

        return queryObj;
    }


    @Override
    public Trainee update(User trainee) {
        return sessionFactory.fromTransaction(session -> {
            session.merge(trainee);
            return (Trainee) trainee;
        });
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName) {
        return sessionFactory.fromTransaction(session -> session.createQuery(GET_NOT_ASSIGNED_TRAINERS, Trainer.class)
                .setParameter("traineeUserName", traineeUserName)
                .getResultList());
    }
}
