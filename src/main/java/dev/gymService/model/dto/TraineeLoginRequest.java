package dev.gymService.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TraineeLoginRequest extends AbstractRequest {
    @NonNull
    private String userName;
    @NonNull
    private String password;
}
