package dev.gymService.service.implementations;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.implmentations.TraineeRepositoryImpl;
import dev.gymService.repository.interfaces.TrainerRepository;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.FileLogger;
import dev.gymService.utills.UserInformationUtility;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = FileLogger.getLogger(TraineeRepositoryImpl.class);
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
        }
        return null;

    }

    @Override
    @Transactional
    public void changeTrainerPassword(String userName, String oldPassword, String newPassword) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        if (trainer != null) {
            // Update trainer if authenticated successfully
            if (isAuthenticated(userName, oldPassword, trainer)) {
                trainer.setPassword(newPassword);
                trainerRepository.updateTrainer(trainer);
            }
        }
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
                trainerRepository.updateTrainer(trainer);
                logger.log(Level.INFO, "Trainer has been updated: " + userName);
                return updatedTrainer;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public void changeTrainerStatus(String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        if (trainer != null) {
            // Update trainer if authenticated successfully
            if (isAuthenticated(userName, password, trainer)) {
                // Toggle status
                Boolean isActive = !trainer.getIsActive();
                trainer.setIsActive(isActive);
                trainerRepository.updateTrainer(trainer);
                logger.log(Level.INFO, "Trainer status has been toggled");
            }
        }
    }

    @Override
    public List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName) {
        Trainer trainer = trainerRepository.getTrainerByUserName(trainerName);
        if (trainer != null) {
            // Return trainer list if authenticated successfully
            if (isAuthenticated(trainerName, password, trainer)) {
                return trainerRepository.getTrainerTrainingList(trainerName, fromDate, toDate, traineeName);
            }
        }
        return null;
    }
}
