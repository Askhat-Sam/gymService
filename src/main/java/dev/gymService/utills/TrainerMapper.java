package dev.gymService.utills;

import dev.gymService.model.Trainer;
import dev.gymService.model.dto.TrainerDTO;
import dev.gymService.model.dto.TrainerProfileResponse;
import dev.gymService.model.dto.TrainerProfileUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TrainerMapper {
    TrainerMapper INSTANCE = Mappers.getMapper(TrainerMapper.class);

    TrainerDTO trainerToTrainerDTOMapper(Trainer trainer);
    TrainerProfileResponse trainerToTrainerProfileResponseMapper(Trainer trainer);

    TrainerProfileUpdateResponse trainerToTrainerProfileUpdateResponseMapper(Trainer trainer);
}
