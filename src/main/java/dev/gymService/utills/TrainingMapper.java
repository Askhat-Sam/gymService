package dev.gymService.utills;

import dev.gymService.model.Training;
import dev.gymService.model.dto.TrainingDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TrainingMapper {
    TrainingDTO trainingToTrainingDTOMapper(Training training);
}
