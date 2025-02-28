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
        return trainingDAO.create(training);
    }

    @Override
    public Training updateTraining(Training training) {
        return trainingDAO.update(training);
    }

    @Override
    public void deleteTraining(Long id) {
        trainingDAO.delete(id);
    }

    @Override
    public Training getTrainingById(Long id) {
        return trainingDAO.getById(id);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingDAO.getAll();
    }

    @Override
    public Long generateTrainingId() {
        return trainingDAO.generateTrainingId();
    }
}
