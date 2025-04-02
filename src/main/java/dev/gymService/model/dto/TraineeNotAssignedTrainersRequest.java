package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeNotAssignedTrainersRequest extends AbstractRequest {
    @NonNull
    private String userName;
    @NonNull
    private String password;
}
