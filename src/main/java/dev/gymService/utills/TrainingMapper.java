package dev.gymService.utills;

import dev.gymService.model.Trainer;
import dev.gymService.model.Training;
import dev.gymService.model.dto.TrainerDTO;
import dev.gymService.model.dto.TrainerProfileResponse;
import dev.gymService.model.dto.TrainerProfileUpdateResponse;
import dev.gymService.model.dto.TrainingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainingMapper {
    TrainingMapper INSTANCE = Mappers.getMapper(TrainingMapper.class);

    TrainingDTO trainingToTrainingDTOMapper(Training training);
}
