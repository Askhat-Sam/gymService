package dev.gymService.service.implementations;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.service.interfaces.TraineeService;

import java.util.Date;
public class TraineeServiceImpl implements TraineeService {

    private TraineeDAO traineeDAO;

    public TraineeServiceImpl(TraineeDAO traineeDAO) {
        this.traineeDAO = traineeDAO;
    }

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
