package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;

public class TraineeDeleteRequest extends AbstractRequest {
    @NotBlank
    private String userName;

    public TraineeDeleteRequest(String userName) {
        this.userName = userName;
    }

    public TraineeDeleteRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TraineeDeleteRequest{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
