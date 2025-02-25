package dev.gymService.service.implementations;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.service.interfaces.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDAO traineeDAO;

    @Override
    public Trainee createTrainee(String firstName, String lastName, String userName, String password, Boolean isActive, Date dateOfBirth, String address, String userId) {
        return null;
    }

    @Override
    public void updateTrainee(String userId) {

    }

    @Override
    public void deleteTrainee(String userId) {
        System.out.println("traineer service");
    }

    @Override
    public Trainee selectTrainee(Long userId) {
        return traineeDAO.getById(userId);
    }
}
