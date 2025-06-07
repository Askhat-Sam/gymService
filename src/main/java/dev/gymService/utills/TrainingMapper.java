package dev.gymService.utills;

import dev.gymService.model.Training;
import dev.gymService.model.dto.TrainingDTO;
import dev.gymService.model.dto.TrainingWorkloadRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TrainingMapper {
    TrainingDTO trainingToTrainingDTOMapper(Training training);

    @Mapping(target = "trainerFirstName", ignore = true)
    @Mapping(target = "trainerLastName", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "actionType", ignore = true)
    TrainingWorkloadRequest trainingToWorkloadRequest(Training training);
}
