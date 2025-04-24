package dev.gymService.service.implementations;

import dev.gymService.model.Role;
import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TraineeRepository;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.UserInformationUtility;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        while (traineeRepository.getByUserName(userName) != null) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }
        String password = UserInformationUtility.generatePassword();
        trainee.setPassword(new BCryptPasswordEncoder().encode(password));
        trainee.setUserName(userName);
        trainee.setRole(Role.TRAINEE);

        return traineeRepository.create(trainee);
    }

    @Override
    public Trainee getTraineeById(Long id) {
        Trainee trainee = traineeRepository.getById(id);
        if (trainee != null) {
                return trainee;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + id + "] not found in DB");
        }
    }

    @Override
    public Trainee getTraineeByUserName(String userName) {
        Trainee trainee = traineeRepository.getByUserName(userName);
        if (trainee != null) {
                return trainee;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public Boolean changeTraineePassword(String userName,  String newPassword) {
        Trainee trainee = traineeRepository.getByUserName(userName);
        if (trainee != null) {
                trainee.setPassword(newPassword);
                traineeRepository.update(trainee);
                return true;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public Trainee updateTrainee(Trainee updatedTrainee, String userName) {
        Trainee trainee = traineeRepository.getByUserName(updatedTrainee.getUserName());
        if (trainee != null) {
                trainee.setFirstName(updatedTrainee.getFirstName());
                trainee.setLastName(updatedTrainee.getLastName());
                trainee.setPassword(updatedTrainee.getPassword());
                trainee.setIsActive(updatedTrainee.getIsActive());
                trainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
                trainee.setAddress(updatedTrainee.getAddress());
                traineeRepository.update(trainee);
                return updatedTrainee;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    public List<Trainer> getNotAssignedTrainers(String traineeUserName) {
        Trainee trainee = traineeRepository.getByUserName(traineeUserName);
        if (trainee != null) {
                return traineeRepository.getNotAssignedTrainers(traineeUserName);
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + traineeUserName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public Boolean deleteTraineeByUserName(String userName) {
        Trainee trainee = traineeRepository.getByUserName(userName);
        if (trainee != null) {
                traineeRepository.deleteByUserName(userName);
                logger.info("Trainee has been deleted: " + trainee.getUserName());
                return true;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public Boolean changeTraineeStatus(String userName) {
        Trainee trainee = traineeRepository.getByUserName(userName);
        if (trainee != null) {
                trainee.setIsActive(!trainee.getIsActive());
                traineeRepository.update(trainee);
                logger.info("Trainee status has been toggled");
                return true;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    public List<Training> getTraineeTrainingList(String traineeName, String fromDate, String toDate, String trainerName, Long trainingTypeId) {
        Trainee trainee = traineeRepository.getByUserName(traineeName);
        if (trainee != null) {
                return traineeRepository.getTraineeTrainingList(traineeName, fromDate, toDate, trainerName, trainingTypeId);
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + traineeName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public List<Trainer> updateTrainersList(String userName, List<Trainer> trainers) {
        Trainee trainee = traineeRepository.getByUserName(userName);
        if (trainee != null) {
                trainee.setTrainers(trainers);
                traineeRepository.update(trainee);
                logger.info("Trainee's trainers list has been updated: " + userName);
                return trainee.getTrainers();
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }
}
