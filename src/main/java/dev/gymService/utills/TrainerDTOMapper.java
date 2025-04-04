package dev.gymService.utills;

import dev.gymService.model.Trainee;
import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.TraineeDTO;
import dev.gymService.model.dto.TrainerDTO;
import dev.gymService.model.dto.TrainingDTO;

public class TrainerDTOMapper {
    public static TrainerDTO toTrainerDTO(Trainer trainer) {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUserName(trainer.getUserName());
        trainerDTO.setFirstName(trainer.getFirstName());
        trainerDTO.setLastName(trainer.getLastName());
        trainerDTO.setSpecialization(trainer.getSpecialization());
        return trainerDTO;
    }






}
