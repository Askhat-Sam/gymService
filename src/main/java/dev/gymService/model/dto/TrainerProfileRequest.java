package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;

public class TrainerProfileRequest extends AbstractRequest{
    @NotBlank
    private String userName;

    public TrainerProfileRequest(String userName) {
        this.userName = userName;
    }

    public TrainerProfileRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "TrainerProfileRequest{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
