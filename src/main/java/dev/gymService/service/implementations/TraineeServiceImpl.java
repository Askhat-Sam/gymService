package dev.gymService.service.implementations;

import dev.gymService.dao.TraineeDAO;
import dev.gymService.model.Trainee;
import dev.gymService.service.interfaces.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;

@Service
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDAO traineeDAO;


    @Override
    public Trainee createTrainee(Trainee trainee) {
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
    public Trainee getTraineeById(Long id) {
        return traineeDAO.getById(id);
    }

    public Long generateTraineeId(){
        return traineeDAO.generateTraineeId();
    }
}
