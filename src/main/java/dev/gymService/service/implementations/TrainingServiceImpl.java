package dev.gymService.service.implementations;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.dao.TrainingDAO;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.utills.FileLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private final TrainingDAO trainingDAO;
    private static final Logger logger = FileLogger.getLogger(TraineeDAO.class);

    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public Training createTraining(Training training) {
        logger.log(Level.INFO, "New training with id [" + training.getTrainingId() + "] has been created");
        return trainingDAO.create(training);
    }

    @Override
    public Training updateTraining(Training training) {
        logger.log(Level.INFO, "The training  with id [" + training.getTrainingId() + "] has been updated");
        return trainingDAO.update(training);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingDAO.delete(id);
        logger.log(Level.INFO, "The trainer  with id [" + id + "] has been deleted");
    }

    @Override
    public Training getTrainingById(Long id) {
        logger.log(Level.INFO, "The training  with id [" + id + "] has been requested");
        return trainingDAO.getById(id);
    }

    @Override
    public List<Training> getAllTrainings() {
        logger.log(Level.INFO, "The list of trainings has been requested");
        return trainingDAO.getAll();
    }

    @Override
    public Long generateTrainingId() {
        return trainingDAO.generateTrainingId();
    }
}
