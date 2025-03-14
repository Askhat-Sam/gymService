package dev.gymService.service.implementations;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.implmentations.TraineeRepositoryImpl;
import dev.gymService.repository.interfaces.TrainerRepository;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.FileLogger;
import dev.gymService.utills.UserInformationUtility;
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
    public Trainer createTrainer(Trainer trainer) {
        long userNameSuffix = 1;
        String userName = trainer.getFirstName().concat(".").concat(trainer.getLastName());
        String originalUserName = userName;
        while (this.getTrainerByUserName(userName, trainer.getPassword()) != null) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setUserName(userName);
        return trainerRepository.create(trainer);
    }

    @Override
    public Trainer getTrainerById(Long id, String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerById(id);
        //  Return trainer if the userName and password is matching
        if (trainer != null && trainer.getUserName().equals(userName) && trainer.getPassword().equals(password)) {
            logger.log(Level.INFO, "Successful authentication for trainer: " + userName);
            return trainer;
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + userName);
            return null;
        }
    }

    @Override
    public Trainer getTrainerByUserName(String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        //  Return trainer if the userName and password is matching
        if (trainer != null && trainer.getUserName().equals(userName) && trainer.getPassword().equals(password)) {
            logger.log(Level.INFO, "Successful authentication for trainer: " + userName);
            return trainer;
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + userName);
            return null;
        }
    }

    @Override
    public void changeTrainerPassword(String userName, String oldPassword, String newPassword) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        //  Update trainer if the userName and password is matching
        if (trainer != null && trainer.getUserName().equals(userName) && trainer.getPassword().equals(oldPassword)) {
            logger.log(Level.INFO, "Successful authentication for trainer: " + userName);
            trainer.setPassword(newPassword);
            trainerRepository.updateTrainer(trainer);
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + userName);
        }
    }

    @Override
    public Trainer updateTrainer(Trainer updatedTrainer, String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerByUserName(updatedTrainer.getUserName());
        //  Check userName and password matching
        if (trainer != null && trainer.getUserName().equals(userName) && trainer.getPassword().equals(password)) {
            logger.log(Level.INFO, "Successful authentication for trainee: " + trainer.getUserName());
            trainer.setFirstName(updatedTrainer.getFirstName());
            trainer.setLastName(updatedTrainer.getLastName());
            trainer.setPassword(updatedTrainer.getPassword());
            trainer.setIsActive(updatedTrainer.getIsActive());
            trainerRepository.updateTrainer(trainer);
            logger.log(Level.INFO, "Trainer has been updated: " + userName);
            return updatedTrainer;
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + userName);
            return null;
        }
    }

    @Override
    public void changeTrainerStatus(String userName, String password) {
        Trainer trainer = trainerRepository.getTrainerByUserName(userName);
        //  Change trainee status if userName and password matching
        if (trainer != null && trainer.getUserName().equals(userName) && trainer.getPassword().equals(password)) {
            // Toggle status
            Boolean isActive = !trainer.getIsActive();
            trainer.setIsActive(isActive);
            trainerRepository.updateTrainer(trainer);
            logger.log(Level.INFO, "Trainer status has been toggled");
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + userName);
        }
    }

    @Override
    public List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName) {
        Trainer trainer = trainerRepository.getTrainerByUserName(trainerName);
        //  Return list of trainings if userName and password matching
        if (trainer != null && trainer.getUserName().equals(trainerName) && trainer.getPassword().equals(password)) {
            logger.log(Level.INFO, "Successful authentication for trainer: " +trainerName);
            return trainerRepository.getTrainerTrainingList(trainerName, fromDate, toDate, traineeName);
        } else {
            logger.log(Level.INFO, "Incorrect userName and password for trainer: " + trainerName);
            return null;
        }
    }
}
