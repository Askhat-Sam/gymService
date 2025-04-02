package dev.gymService.utills;

import dev.gymService.model.Trainee;
import dev.gymService.model.dto.TraineeDTO;

public class TraineeDTOMapper {
    public static TraineeDTO toTraineeDTO(Trainee trainee) {
        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUserName(trainee.getUserName());
        traineeDTO.setFirstName(trainee.getFirstName());
        traineeDTO.setLastName(trainee.getLastName());
        return traineeDTO;
    }
}
