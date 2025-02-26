package dev.gymService.service.implementations;

import dev.gymService.dao.TrainingDAO;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private final TrainingDAO trainingDAO;

    public TrainingServiceImpl(TrainingDAO trainingDAO) {
        this.trainingDAO = trainingDAO;
    }

    @Override
    public void createTraining(Training training) {
        trainingDAO.create(training);
    }

    @Override
    public void updateTraining(Training training) {
        trainingDAO.update(training);
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
}
