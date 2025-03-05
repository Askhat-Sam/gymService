package dev.gymService.service.implementations;

import dev.gymService.dao.interfaces.TrainerRepository;
import dev.gymService.model.Trainer;
import dev.gymService.service.interfaces.TrainerService;
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
//        long userNameSuffix = 1;
//        String userName = trainer.getFirstName().concat(".").concat(trainer.getLastName());
//        String originalUserName = userName;
//        while (this.getTrainerByUserName(userName) != null) {
//            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
//        }
//        trainer.setPassword(UserInformationUtility.generatePassword());
//        trainer.setUserName(userName);
        return trainerRepository.create(trainer);
    }

    @Override
    public Trainer getTrainerById(Long id) {
        return trainerRepository.getTrainerById(id);
    }

    @Override
    public Trainer getTrainerByUserName(String userName) {
        return trainerRepository.getTrainerByUserName(userName);
    }

    @Override
    public void changeTrainerPassword(String userName, String newPassword) {
        trainerRepository.changeTrainerPassword(userName, newPassword);
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        return trainerRepository.updateTrainee(trainer);
    }
}
