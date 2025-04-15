package dev.gymService.utills;

import dev.gymService.model.Trainer;
import dev.gymService.model.dto.TrainerDTO;
import dev.gymService.model.dto.TrainerProfileResponse;
import dev.gymService.model.dto.TrainerProfileUpdateResponse;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TrainerMapper {
    TrainerDTO trainerToTrainerDTOMapper(Trainer trainer);
    TrainerProfileResponse trainerToTrainerProfileResponseMapper(Trainer trainer);

    TrainerProfileUpdateResponse trainerToTrainerProfileUpdateResponseMapper(Trainer trainer);
}
