package dev.gymService.service.implementations;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.model.User;
import dev.gymService.service.interfaces.TraineeService;
import dev.gymService.utills.UserInformationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDAO traineeDAO;


    @Override
    public Trainee createTrainee(Trainee trainee) {
        long userNameSuffix = 1;
        String userName = trainee.getFirstName().concat(".").concat(trainee.getLastName());
        String originalUserName = userName;
        while (this.getTraineeByUserName(userName) != null) {
            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
        }
        trainee.setPassword(UserInformationUtility.generatePassword());
        trainee.setUserName(userName);
        return traineeDAO.create(trainee);
    }

    @Override
    public Trainee updateTrainee(Trainee trainee) {
        return traineeDAO.update(trainee);
    }

    @Override
    public void deleteTrainee(Long id) {
        traineeDAO.delete(id);
    }

    public List<Trainee> getAllTrainee() {
        return traineeDAO.getAll();
    }

    @Override
    public Trainee getTraineeByUserName(String userName) {
        return traineeDAO.getTraineeByUserName(userName);
    }

    @Override
    public Trainee getTraineeById(Long id) {
        return traineeDAO.getById(id);
    }


}
