package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;


public class TrainerStatusToggleRequest {
    @NotBlank
    private String userName;
    @NonNull
    private Boolean isActive;

    public TrainerStatusToggleRequest(String userName, @NonNull Boolean isActive) {
        this.userName = userName;
        this.isActive = isActive;
    }

    public TrainerStatusToggleRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }


    @Override
    public String toString() {
        return "TrainerStatusToggleRequest{" +
                "userName='" + userName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
