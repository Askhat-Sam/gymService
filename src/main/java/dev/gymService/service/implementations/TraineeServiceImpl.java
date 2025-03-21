package dev.gymService.service.implementations;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.implmentations.TraineeRepositoryImpl;
import dev.gymService.repository.interfaces.TraineeRepository;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.FileLogger;
import dev.gymService.utills.UserInformationUtility;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TraineeServiceImpl implements TraineeService {
    private static final Logger logger = FileLogger.getLogger(TraineeRepositoryImpl.class);

    private final TraineeRepository traineeRepository;

    public TraineeServiceImpl(TraineeRepository traineeRepository) {
        this.traineeRepository = traineeRepository;
    }

    @Override
    @Transactional
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
        Trainee trainee = traineeRepository.getTraineeById(id);
        if (trainee != null) {
            // Return trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                return trainee;
            }
        }
        return null;
    }

    @Override
    public Trainee getTraineeByUserName(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            // Return trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                return trainee;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void changeTraineePassword(String userName, String oldPassword, String newPassword) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            // Update trainee if authenticated successfully
            if (isAuthenticated(userName, oldPassword, trainee)) {
                trainee.setPassword(newPassword);
                traineeRepository.updateTrainee(trainee);
            }
        }
    }

    @Override
    @Transactional
    public Trainee updateTrainee(Trainee updatedTrainee, String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(updatedTrainee.getUserName());
        if (trainee != null) {
            // Return trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                trainee.setFirstName(updatedTrainee.getFirstName());
                trainee.setLastName(updatedTrainee.getLastName());
                trainee.setPassword(updatedTrainee.getPassword());
                trainee.setIsActive(updatedTrainee.getIsActive());
                trainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
                trainee.setAddress(updatedTrainee.getAddress());
                traineeRepository.updateTrainee(trainee);
                logger.log(Level.INFO, "Trainee has been updated: " + userName);
                return updatedTrainee;
            }
        }
        return null;
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(traineeUserName);
        if (trainee != null) {
            //  Return trainers list if authenticated successfully
            if (isAuthenticated(traineeUserName, password, trainee)) {
                return traineeRepository.getNotAssignedTrainers(traineeUserName);
            }
        }
        return null;

    }

    @Override
    @Transactional
    public void deleteTraineeByUserName(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            //  Delete trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                traineeRepository.deleteTraineeByUserName(userName);
                logger.log(Level.INFO, "Trainee has been deleted: " + trainee.getUserName());
            }
        }
    }

    @Override
    @Transactional
    public void changeTraineeStatus(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            //  Return trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                // Toggle status
                trainee.setIsActive(!trainee.getIsActive());
                traineeRepository.updateTrainee(trainee);
                logger.log(Level.INFO, "Trainee status has been toggled");
            }
        }
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName) {
        Trainee trainee = traineeRepository.getTraineeByUserName(traineeName);
        if (trainee != null) {
            //  Return trainee's training list if authenticated successfully
            if (isAuthenticated(traineeName, password, trainee)) {
                return traineeRepository.getTraineeTrainingList(traineeName, fromDate, toDate, trainerName);
            }
        }
        return null;

    }

    @Override
    @Transactional
    public void updateTrainersList(String userName, String password, List<Trainer> trainers) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            //  Return trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                trainee.setTrainers(trainers);
                traineeRepository.updateTrainee(trainee);
                logger.log(Level.INFO, "Trainee's trainers list has been updated: " + userName);
            }
        }
    }
}
