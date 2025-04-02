package dev.gymService.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TraineeProfileRequest extends AbstractRequest{
    @NonNull
    private String userName;
    @NonNull
    private String password;
}
