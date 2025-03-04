package dev.gymService.service.implementations;

import dev.gymService.dao.TrainerDAO;
import dev.gymService.model.Trainer;
import dev.gymService.model.User;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.UserInformationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private final TrainerDAO trainerDAO;

    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        long userNameSuffix = 1;
        String userName = trainer.getFirstName().concat(".").concat(trainer.getLastName());
        String originalUserName = userName;
        while (this.getTrainerByUserName(userName) != null) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }
        trainer.setPassword(UserInformationUtility.generatePassword());
        trainer.setUserName(userName);
        return trainerDAO.create(trainer);
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        return trainerDAO.update(trainer);
    }

    @Override
    public void deleteTrainer(Long id) {
        trainerDAO.delete(id);
    }

    @Override
    public Trainer getTrainerById(Long id) {
        return trainerDAO.getById(id);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.getAll();
    }

    @Override
    public Trainer getTrainerByUserName(String userName) {
        return trainerDAO.getTrainerByUserName(userName);
    }
}
