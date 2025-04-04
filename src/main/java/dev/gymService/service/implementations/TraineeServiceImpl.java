package dev.gymService.service.implementations;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TraineeRepository;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.UserInformationUtility;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TraineeServiceImpl implements TraineeService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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

        while (traineeRepository.getTraineeByUserName(userName) != null) {
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
        } else {
            logger.error("NoSuchElementException has been thrown");            throw new NoSuchElementException("User [" + userName + "] not found in DB");
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
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean changeTraineePassword(String userName, String oldPassword, String newPassword) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            // Update trainee if authenticated successfully
            if (isAuthenticated(userName, oldPassword, trainee)) {
                trainee.setPassword(newPassword);
                traineeRepository.updateTrainee(trainee);
                return true;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
        return false;
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
//                logger.log(Level.INFO, "Trainee has been updated: " + userName);
                return updatedTrainee;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
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
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + traineeUserName + "] not found in DB");
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteTraineeByUserName(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            //  Delete trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                traineeRepository.deleteTraineeByUserName(userName);
                logger.info("Trainee has been deleted: " + trainee.getUserName());
                return true;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean changeTraineeStatus(String userName, String password) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            //  Return trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                // Toggle status
                trainee.setIsActive(!trainee.getIsActive());
                traineeRepository.updateTrainee(trainee);
                logger.info("Trainee status has been toggled");
                return true;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
        return false;
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String password, String fromDate, String toDate, String trainerName, Long trainingTypeId) {
        Trainee trainee = traineeRepository.getTraineeByUserName(traineeName);
        if (trainee != null) {
            //  Return trainee's training list if authenticated successfully
            if (isAuthenticated(traineeName, password, trainee)) {
                return traineeRepository.getTraineeTrainingList(traineeName, fromDate, toDate, trainerName, trainingTypeId);
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + traineeName + "] not found in DB");
        }
        return null;
    }

    @Override
    @Transactional
    public List<Trainer> updateTrainersList(String userName, String password, List<Trainer> trainers) {
        Trainee trainee = traineeRepository.getTraineeByUserName(userName);
        if (trainee != null) {
            //  Return trainee if authenticated successfully
            if (isAuthenticated(userName, password, trainee)) {
                trainee.setTrainers(trainers);
                traineeRepository.updateTrainee(trainee);
                logger.info("Trainee's trainers list has been updated: " + userName);
                return trainee.getTrainers();
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
        return null;
    }
}
