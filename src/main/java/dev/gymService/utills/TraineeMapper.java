package dev.gymService.utills;

import dev.gymService.model.Trainee;
import dev.gymService.model.dto.TraineeDTO;
import dev.gymService.model.dto.TraineeProfileResponse;
import dev.gymService.model.dto.TraineeProfileUpdateResponse;
import dev.gymService.model.dto.TraineeRegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TraineeMapper {
    TraineeDTO traineeToTraineeDTOMapper(Trainee trainee);
    TraineeProfileResponse traineeToTraineeProfileResponse(Trainee trainee);
    TraineeProfileUpdateResponse traineeToTraineeProfileUpdateResponse(Trainee trainee);
    @Mapping(source = "address", target="address", defaultValue = "Not provided")
    @Mapping(source = "dateOfBirth", target="dateOfBirth", defaultValue = "Not provided")
    Trainee traineeRegistrationRequestToTrainee(TraineeRegistrationRequest traineeRegistrationRequest);
}
