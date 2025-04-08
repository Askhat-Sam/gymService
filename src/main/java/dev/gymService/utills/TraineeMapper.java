package dev.gymService.utills;

import dev.gymService.model.Trainee;
import dev.gymService.model.dto.TraineeDTO;
import dev.gymService.model.dto.TraineeProfileResponse;
import dev.gymService.model.dto.TraineeProfileUpdateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TraineeMapper {
    TraineeMapper INSTANCE = Mappers.getMapper(TraineeMapper.class);

    TraineeDTO traineeToTraineeDTOMapper(Trainee trainee);
    TraineeProfileResponse traineeToTraineeProfileResponse(Trainee trainee);
    TraineeProfileUpdateResponse traineeToTraineeProfileUpdateResponse(Trainee trainee);
}
