package dev.gymService.service.implementations;

import dev.gymService.repository.interfaces.TraineeRepository;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.UserInformationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeRepository traineeRepository;

    @Override
    public Trainee createTrainee(Trainee trainee) {
        long userNameSuffix = 1;
        String userName = trainee.getFirstName().concat(".").concat(trainee.getLastName());
        String originalUserName = userName;
        while (this.getTraineeByUserName(userName, trainee.getPassword()) != null) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }
        trainee.setPassword(UserInformationUtility.generatePassword());
        trainee.setUserName(userName);
        return traineeRepository.create(trainee);
    }

    @Override
    public Trainee getTraineeById(Long id, String userName, String password) {
        return traineeRepository.getTraineeById(id, userName, password);
    }

    @Override
    public Trainee getTraineeByUserName(String userName, String password) {
        return traineeRepository.getTraineeByUserName(userName, password);
    }

    @Override
    public Trainee updateTrainee(Trainee trainee) {
        return traineeRepository.updateTrainee(trainee);
    }

    @Override
    public void deleteTraineeByUserName(String userName, String password) {
        traineeRepository.deleteTraineeByUserName(userName, password);
    }

    @Override
    public void changeTraineeStatus(String userName, String password) {
        traineeRepository.changeTraineeStatus(userName, password);
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName) {
        return traineeRepository.getTraineeTrainingList(traineeName, password, fromDate, toDate, trainerName);
    }

    @Override
    public void changeTraineePassword(String userName, String oldPassword, String newPassword) {
        traineeRepository.changeTraineePassword(userName, oldPassword, newPassword);
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName, String password) {
        return traineeRepository.getNotAssignedTrainers(traineeUserName, password);
    }

    @Override
    public void updateTrainersList(String traineeUserName, String password, List<Trainer> trainers) {
       traineeRepository.updateTrainersList(traineeUserName, password, trainers);
    }
}
