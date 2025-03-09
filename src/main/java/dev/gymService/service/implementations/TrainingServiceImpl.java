package dev.gymService.service.implementations;

import dev.gymService.dao.interfaces.TrainingRepository;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    public Training addTraining(Training training) {
        return trainingRepository.addTraining(training);
    }
}


