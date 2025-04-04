package dev.gymService.utills;

import dev.gymService.model.Training;
import dev.gymService.model.dto.TrainingDTO;

public class TrainingDTOMapper {
    public static TrainingDTO toTrainingDTO(Training training){
        TrainingDTO trainingDTO = new TrainingDTO();
        trainingDTO.setTrainingName(training.getTrainingName());
        trainingDTO.setTrainingDate(training.getTrainingDate());
        trainingDTO.setTrainingTypeId(training.getTrainingTypeId());
        trainingDTO.setTrainingDuration(training.getTrainingDuration());
        trainingDTO.setTrainerName(training.getTrainer().getUserName());
        return trainingDTO;
    }
}
