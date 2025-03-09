package dev.gymService.service.implementations;

import dev.gymService.dao.interfaces.TrainerRepository;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.service.interfaces.TrainerService;
import dev.gymService.utills.UserInformationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrainerServiceImpl implements TrainerService {
    @Autowired
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
        return trainerRepository.getTrainerById(id, userName, password);
    }

    @Override
    public Trainer getTrainerByUserName(String userName, String password) {
        return trainerRepository.getTrainerByUserName(userName, password);
    }

    @Override
    public void changeTrainerPassword(String userName, String oldPassword, String newPassword) {
        trainerRepository.changeTrainerPassword(userName, oldPassword, newPassword);
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        return trainerRepository.updateTrainee(trainer);
    }

    @Override
    public void changeTrainerStatus(String userName, String password) {
        trainerRepository.changeTrainerStatus(userName, password);
    }
    @Override
    public List<Training> getTrainerTrainingList(String trainerName, String password, String fromDate, String toDate, String traineeName) {
        return trainerRepository.getTrainerTrainingList(trainerName, password, fromDate, toDate, traineeName);
    }
}
