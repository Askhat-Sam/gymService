package dev.gymService.service.implementations;

import dev.gymService.dao.interfaces.TraineeRepository;
import dev.gymService.model.Trainee;
import dev.gymService.service.interfaces.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeRepository traineeRepository;


    @Override
    public Trainee createTrainee(Trainee trainee) {
//        long userNameSuffix = 1;
//        String userName = trainee.getFirstName().concat(".").concat(trainee.getLastName());
//        String originalUserName = userName;
//        while (this.getTraineeByUserName(userName) != null) {
//            userName = originalUserName.concat(String.valueOf(userNameSuffix++));
//        }
//        trainee.setPassword(UserInformationUtility.generatePassword());
//        trainee.setUserName(userName);
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

    @Override
    public Trainee updateTrainee(Trainee trainee) {
        return traineeRepository.updateTrainee(trainee);
    }

    @Override
    public void deleteTraineeByUserName(String userName) {
        traineeRepository.deleteTraineeByUserName(userName);
    }

    @Override
    public void changeTraineeStatus(String userName) {
        traineeRepository.changeTraineeStatus(userName);
    }

    @Override
    public void changeTraineePassword(String userName,String newPassword) {
        traineeRepository.changeTraineePassword(userName, newPassword);
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
//    public Trainee getTraineeByUserName(String userName) {
//        return traineeDAO.getTraineeByUserName(userName);
//    }
//
//    @Override
//    public Trainee getTraineeById(Long id) {
//        return traineeDAO.getById(id);
//    }


}
