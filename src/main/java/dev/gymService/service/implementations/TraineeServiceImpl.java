package dev.gymService.service.implementations;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.User;
import dev.gymService.repository.implmentations.TraineeRepositoryImpl;
import dev.gymService.repository.interfaces.TraineeRepository;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.FileLogger;
import dev.gymService.utills.UserInformationUtility;
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
        //  Return trainee if the userName and password is matching
        if (isAuthenticated(userName, password, trainee)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + userName);
            return trainee;
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + userName);
            return null;
        }
    }

    @Override
    public Trainee getTraineeByUserName(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        //  Return trainee if the userName and password is matching
        if (isAuthenticated(userName, password, trainee)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + userName);
            return trainee;
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + userName);
            return null;
        }
    }

    @Override
    public void changeTraineePassword(String userName, String oldPassword, String newPassword) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        //  Update trainee if the userName and password is matching
        if (isAuthenticated(userName, oldPassword, trainee)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + userName);
            trainee.setPassword(newPassword);
            traineeRepository.updateTrainee(trainee);
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + userName);
        }
    }

    @Override
    public Trainee updateTrainee(Trainee updatedTrainee, String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(updatedTrainee.getUserName());
        //  Check userName and password matching
        if (isAuthenticated(userName, password, trainee)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + trainee.getUserName());
            trainee.setFirstName(updatedTrainee.getFirstName());
            trainee.setLastName(updatedTrainee.getLastName());
            trainee.setPassword(updatedTrainee.getPassword());
            trainee.setIsActive(updatedTrainee.getIsActive());
            trainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
            trainee.setAddress(updatedTrainee.getAddress());
            traineeRepository.updateTrainee(trainee);
            logger.log(Level.INFO, "Trainee has been updated: " + userName);
            return updatedTrainee;
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + updatedTrainee.getUserName());
            return null;
        }
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(traineeUserName);
        //  Get list of trainers if userName and password matching
        if (isAuthenticated(traineeUserName, password, trainee)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + traineeUserName);
            return traineeRepository.getNotAssignedTrainers(traineeUserName);
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + traineeUserName);
            return null;
        }
    }

    @Override
    public void deleteTraineeByUserName(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        //  Delete trainee if userName and password matching
        if (isAuthenticated(userName, password, trainee)) {
            traineeRepository.deleteTraineeByUserName(userName);
            logger.log(Level.INFO, "Trainee has been deleted: " + trainee.getUserName());
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + userName);
        }
    }

    @Override
    public void changeTraineeStatus(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        //  Change trainee status if userName and password matching
        if (isAuthenticated(userName, password, trainee)) {
            // Toggle status
            Boolean isActive = !trainee.getIsActive();
            trainee.setIsActive(isActive);
            traineeRepository.updateTrainee(trainee);
            logger.log(Level.INFO, "Trainee status has been toggled");
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + userName);
        }
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName) {
        Trainee trainee = traineeRepository.getTraineeByUserName(traineeName);
        //  Return list of trainings if userName and password matching
        if (isAuthenticated(traineeName, password, trainee)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + trainee.getUserName());
            return traineeRepository.getTraineeTrainingList(traineeName, fromDate, toDate, trainerName);
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + traineeName);
            return null;
        }
    }


    @Override
    public void updateTrainersList(String userName, String password, List<Trainer> trainers) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        //  Update trainee's trainings list if userName and password matching
        if (isAuthenticated(userName, password, trainee)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + trainee.getUserName());
            trainee.setTrainers(trainers);
            traineeRepository.updateTrainee(trainee);
            logger.log(Level.INFO, "Trainee's trainers list has been updated: " + userName);
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainee: " + userName);
        }
    }

}
