package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;


public class TraineeStatusToggleRequest extends AbstractRequest {
    @NotBlank
    private String userName;
    @NonNull
    private Boolean isActive;

    public TraineeStatusToggleRequest(String userName, @NonNull Boolean isActive) {
        this.userName = userName;
        this.isActive = isActive;
    }

    public TraineeStatusToggleRequest() {
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
        return "TraineeStatusToggleRequest{" +
                "userName='" + userName + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
