package dev.gymService.service.implementations;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TrainerRepository;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.UserInformationUtility;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class TrainerServiceImpl implements TrainerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    @Transactional
    public Trainer createTrainer(Trainer trainer) {
        long userNameSuffix = 1;
        String userName = trainer.getFirstName().concat(".").concat(trainer.getLastName());
        String originalUserName = userName;
        while (trainerRepository.getTrainerByUserName(userName) != null) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setUserName(userName);
        return trainerRepository.create(trainer);
    }

    @Override
    public Trainer getTrainerById(Long id, String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerById(id);
        if (trainer != null) {
            // Return trainer if authenticated successfully
            if (isAuthenticated(userName, password, trainer)) {
                return trainer;
            }
        }else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName +"] not found in DB");
        }
        return null;
    }

    @Override
    public Trainer getTrainerByUserName(String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        if (trainer != null) {
            // Return trainer if authenticated successfully
            if (isAuthenticated(userName, password, trainer)) {
                return trainer;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName +"] not found in DB");
        }
        return null;

    }

    @Override
    @Transactional
    public Boolean changeTrainerPassword(String userName, String oldPassword, String newPassword) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        if (trainer != null) {
            // Update trainer if authenticated successfully
            if (isAuthenticated(userName, oldPassword, trainer)) {
                trainer.setPassword(newPassword);
                trainerRepository.updateTrainer(trainer);
                return true;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName +"] not found in DB");
        }
        return false;
    }

    @Override
    @Transactional
    public Trainer updateTrainer(Trainer updatedTrainer, String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerByUserName(updatedTrainer.getUserName());
        if (trainer != null) {
            // Update trainer if authenticated successfully
            if (isAuthenticated(userName, password, trainer)) {
                trainer.setFirstName(updatedTrainer.getFirstName());
                trainer.setLastName(updatedTrainer.getLastName());
                trainer.setPassword(updatedTrainer.getPassword());
                trainer.setIsActive(updatedTrainer.getIsActive());
                logger.info("Trainer has been updated: " + userName);
                trainerRepository.updateTrainer(trainer);
                return updatedTrainer;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName +"] not found in DB");
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean changeTrainerStatus(String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        if (trainer != null) {
            // Update trainer if authenticated successfully
            if (isAuthenticated(userName, password, trainer)) {
                // Toggle status
                Boolean isActive = !trainer.getIsActive();
                trainer.setIsActive(isActive);
                logger.info("Trainer status has been toggled");
                trainerRepository.updateTrainer(trainer);
                return true;
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName +"] not found in DB");
        }
        return false;
    }

    @Override
    public List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName, Long trainingTypeId) {
        Trainer trainer = trainerRepository.getTrainerByUserName(trainerName);
        if (trainer != null) {
            // Return trainer list if authenticated successfully
            if (isAuthenticated(trainerName, password, trainer)) {
                return trainerRepository.getTrainerTrainingList(trainerName, fromDate, toDate, traineeName, trainingTypeId);
            }
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + trainerName +"] not found in DB");
        }
        return null;
    }
}
