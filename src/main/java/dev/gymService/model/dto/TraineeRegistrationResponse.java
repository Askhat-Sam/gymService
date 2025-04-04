package dev.gymService.model.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class TraineeRegistrationResponse {
    @NonNull
    private String userName;
    @NonNull
    private String password;

    public TraineeRegistrationResponse(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
