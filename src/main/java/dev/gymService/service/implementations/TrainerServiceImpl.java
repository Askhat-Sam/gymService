package dev.gymService.service.implementations;

import dev.gymService.dao.TrainerDAO;
import dev.gymService.model.Trainer;
import dev.gymService.service.interfaces.TraineeService;

import java.util.List;

public class TrainerServiceImpl implements TraineeService {
    private final TrainerDAO trainerDAO;

    public TrainerServiceImpl(TrainerDAO trainerDAO) {
        this.trainerDAO = trainerDAO;
    }

    @Override
    public void createTrainer(Trainer trainer) {
        trainerDAO.create(trainer);
    }

    @Override
    public void updateTrainer(Trainer trainer) {
        trainerDAO.update(trainer);
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
}
