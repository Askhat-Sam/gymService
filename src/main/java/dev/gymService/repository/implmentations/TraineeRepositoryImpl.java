package dev.gymService.repository.implmentations;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TraineeRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TraineeRepositoryImpl implements TraineeRepository {
    private static final String SELECT_BY_USERNAME = "SELECT t FROM Trainee t WHERE t.userName = :userName";
    private static final String DELETE_BY_USERNAME = "DELETE FROM Trainee WHERE userName = :userName";
    private static final String GET_TRAINEE_TRAINING_LIST = "SELECT t FROM Training t " +
            "WHERE t.trainee.userName = :traineeName " +
            "AND t.trainingDate >= :fromDate " +
            "AND t.trainingDate <= :toDate " +
            "AND t.trainer.userName = :trainerName";
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
    public Trainee create(Trainee trainee) {
        sessionFactory.getCurrentSession().persist(trainee);
        return trainee;
    }

    @Override
    public Trainee getTraineeById(Long id) {
        return sessionFactory.getCurrentSession().get(Trainee.class, id);
    }

    @Override
    public Trainee getTraineeByUserName(String userName) {
        return sessionFactory.getCurrentSession().createQuery(SELECT_BY_USERNAME, Trainee.class)
                .setParameter("userName", userName)
                .uniqueResult();
    }

    @Override
    public void deleteTraineeByUserName(String userName) {
        sessionFactory.getCurrentSession().createMutationQuery(DELETE_BY_USERNAME)
                .setParameter("userName", userName)
                .executeUpdate();
}


    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String fromDate, String toDate, String trainerName) {
        return sessionFactory.getCurrentSession().createQuery(GET_TRAINEE_TRAINING_LIST, Training.class)
                .setParameter("traineeName", traineeName)
                .setParameter("fromDate", LocalDate.parse(fromDate))
                .setParameter("toDate", LocalDate.parse(toDate))
                .setParameter("trainerName", trainerName)
                .getResultList();
    }


    @Override
    public Trainee updateTrainee(Trainee trainee) {
        sessionFactory.getCurrentSession().merge(trainee);
        return trainee;
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName) {
        return sessionFactory.getCurrentSession().createQuery(GET_NOT_ASSIGNED_TRAINERS, Trainer.class)
                .setParameter("traineeUserName", traineeUserName)
                .getResultList();
    }
}
