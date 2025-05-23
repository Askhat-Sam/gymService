package dev.gymService.service.implementations;

import dev.gymService.model.Training;
import dev.gymService.model.TrainingType;
import dev.gymService.repository.interfaces.TrainingRepository;
import dev.gymService.service.interfaces.TrainingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;

    public TrainingServiceImpl(TrainingRepository trainingRepository) {
        this.trainingRepository = trainingRepository;
    }

    @Override
    @Transactional
    public Training addTraining(Training training) {
        return trainingRepository.addTraining(training);
    }

    @Override
    public TrainingType getTrainingTypeIdByTrainingName(String trainingName) {
        return trainingRepository.getTrainingTypeIdByTrainingName(trainingName);
    }

    @Override
    public List<TrainingType> getTrainingTypes() {
        return trainingRepository.getTrainingTypes();
    }
}
