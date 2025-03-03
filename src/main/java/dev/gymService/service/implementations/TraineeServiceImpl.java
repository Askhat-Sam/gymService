package dev.gymService.service.implementations;

//import dev.gymService.dao.TraineeDAO;
import dev.gymService.dao.interfaces.TraineeRepository;
import dev.gymService.model.Trainee;
import dev.gymService.model.User;
import dev.gymService.service.interfaces.TraineeService;
//import dev.gymService.utills.UserInformationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeRepository traineeRepository;

    @Override
    public Trainee createTrainee(Trainee trainee) {
//        trainee.setPassword(UserInformationUtility.generatePassword());
//        trainee.setUserName(UserInformationUtility.generateUserName(trainee.getFirstName(), trainee.getLastName(),
//                this.getAllTrainee().stream().map(User::getUserName).collect(Collectors.toList())));
        return traineeRepository.create(trainee);
    }

    @Override
    public Trainee getTraineeById(Long id) {
        return traineeRepository.getTraineeById(id);
    }

    @Override
    public Trainee getTraineeByUserName(String userName) {
        return traineeRepository.getTraineeByUserName(userName);
    }

//    @Override
//    public Trainee updateTrainee(Trainee trainee) {
//        return traineeDAO.update(trainee);
//    }
//
//    @Override
//    public void deleteTrainee(Long id) {
//        traineeDAO.delete(id);
//    }
//
//    public List<Trainee> getAllTrainee() {
//        return traineeDAO.getAll();
//    }
//
//    @Override
//    public Trainee getTraineeById(Long id) {
//        return traineeDAO.getById(id);
//    }
}
