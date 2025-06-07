package dev.gymService.service.implementations;

import dev.gymService.model.*;
import dev.gymService.repository.interfaces.TrainingRepository;
import dev.gymService.service.interfaces.TrainingService;
import dev.gymService.service.interfaces.WorkloadServiceClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final WorkloadServiceClient workloadServiceClient;

    public TrainingServiceImpl(TrainingRepository trainingRepository, WorkloadServiceClient workloadServiceClient) {
        this.trainingRepository = trainingRepository;
        this.workloadServiceClient = workloadServiceClient;
    }

    @Override
    @Transactional
    public Training addTraining(Training training, ActionType actionType, Trainee trainee, Trainer trainer, HttpServletRequest httpServletRequest) {
        trainingRepository.addTraining(training);

        // Calling workloadService based on provided implementation (REST or messaging) of WorkloadServiceClient interface
        workloadServiceClient.updateMicroservice(training, actionType, trainee, trainer,httpServletRequest);

        return training;
    }

    @Override
    public TrainingType getTrainingTypeIdByTrainingName(String trainingName) {
        return trainingRepository.getTrainingTypeIdByTrainingName(trainingName);
    }

    @Override
    public List<TrainingType> getTrainingTypes() {
        return trainingRepository.getTrainingTypes();
    }

    @Override
    public TrainingWorkload getWorkloadSummary(String username, HttpServletRequest httpServletRequest) {

        return workloadServiceClient.getWorkloadSummary(username, httpServletRequest);
    }
}
