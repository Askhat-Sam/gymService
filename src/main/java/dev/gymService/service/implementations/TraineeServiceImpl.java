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
    public Trainee createTrainee(Trainee trainee) {
        logger.log(Level.INFO, "New trainee  with id [" + trainee.getUserId() + "] has been created");
        return traineeDAO.create(trainee);
    }


    @Override
    public Trainee updateTrainee(Trainee trainee) {

        logger.log(Level.INFO, "The trainee  with id [" + trainee.getUserId() + "] has been updated");
        return traineeDAO.update(trainee);
    }

    @Override
    public void deleteTrainee(Long id) {
        traineeDAO.delete(id);
        logger.log(Level.INFO, "The trainee  with id [" + id + "] has been deleted");
    }

    public List<Trainee> getAllTrainee() {
        logger.log(Level.INFO, "The list of trainees has been requested");
        return traineeDAO.getAll();
    }

    @Override
    public Trainee getTraineeById(Long id) {
        logger.log(Level.INFO, "The trainee  with id [" + id + "] has been requested");
        return traineeDAO.getById(id);
    }

    public Long generateTraineeId(){
        return traineeDAO.generateTraineeId();
    }
}
