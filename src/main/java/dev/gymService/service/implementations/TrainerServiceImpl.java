package dev.gymService.service.implementations;

import dev.gymService.model.Role;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.repository.interfaces.TrainerRepository;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.UserInformationUtility;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        while (trainerRepository.getByUserName(userName) != null) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }
        String password = UserInformationUtility.generatePassword();
        trainer.setPassword(new BCryptPasswordEncoder().encode(password));
        trainer.setUserName(userName);
        trainer.setRole(Role.TRAINER);
        return trainerRepository.create(trainer);
    }

    @Override
    public Trainer getTrainerById(Long id) {
        Trainer trainer = trainerRepository.getById(id);
        if (trainer != null) {
            return trainer;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + id + "] not found in DB");
        }
    }

    @Override
    public Trainer getTrainerByUserName(String userName) {
        Trainer trainer = trainerRepository.getByUserName(userName);
        if (trainer != null) {
            return trainer;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public Boolean changeTrainerPassword(String userName, String newPassword) {
        Trainer trainer = trainerRepository.getByUserName(userName);
        if (trainer != null) {
            trainer.setPassword(newPassword);
            trainerRepository.update(trainer);
            return true;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public Trainer updateTrainer(Trainer updatedTrainer, String userName) {
        Trainer trainer = trainerRepository.getByUserName(updatedTrainer.getUserName());
        if (trainer != null) {
            trainer.setFirstName(updatedTrainer.getFirstName());
            trainer.setLastName(updatedTrainer.getLastName());
            trainer.setPassword(updatedTrainer.getPassword());
            trainer.setIsActive(updatedTrainer.getIsActive());
            logger.info("Trainer has been updated: " + userName);
            trainerRepository.update(trainer);
            return updatedTrainer;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    @Transactional
    public Boolean changeTrainerStatus(String userName) {
        Trainer trainer = trainerRepository.getByUserName(userName);
        if (trainer != null) {
            // Toggle status
            Boolean isActive = !trainer.getIsActive();
            trainer.setIsActive(isActive);
            logger.info("Trainer status has been toggled");
            trainerRepository.update(trainer);
            return true;
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + userName + "] not found in DB");
        }
    }

    @Override
    public List<Training> getTrainerTrainingList(String trainerName, String fromDate, String toDate, String traineeName, Long trainingTypeId) {
        Trainer trainer = trainerRepository.getByUserName(trainerName);
        if (trainer != null) {
            return trainerRepository.getTrainerTrainingList(trainerName, fromDate, toDate, traineeName, trainingTypeId);
        } else {
            logger.error("NoSuchElementException has been thrown");
            throw new NoSuchElementException("User [" + trainerName + "] not found in DB");
        }
    }
}
