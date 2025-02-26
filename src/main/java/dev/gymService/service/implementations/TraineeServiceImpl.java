package dev.gymService.service.implementations;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDAO traineeDAO;

    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);

    @Override
    public void createTrainee(Trainee trainee) {
        traineeDAO.create(trainee);
    }

    @Override
    public void updateTrainee(Trainee trainee) {
        logger.log(Level.INFO, "Calling traineeDAO.update method for trainee with id: " + trainee.getUserId());
        traineeDAO.update(trainee);
    }

    @Override
    public void deleteTrainee(Long id) {
        logger.log(Level.INFO, "Calling traineeDAO.delete method for trainee with id: " +id);
        traineeDAO.delete(id);
    }

    public List<Trainee> getAlTrainee() {
        logger.log(Level.INFO, "Calling traineeDAO.getAll method");
        return traineeDAO.getAll();
    }

    @Override
    public Trainee getTraineeById(Long id) {
        logger.log(Level.INFO, "Calling traineeDAO.getById method for trainee with id: " +id);
        return traineeDAO.getById(id);
    }
}
