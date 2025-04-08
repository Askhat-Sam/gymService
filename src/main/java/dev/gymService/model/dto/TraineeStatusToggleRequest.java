package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;


public class TraineeStatusToggleRequest extends AbstractRequest{
    @NotBlank
    private String userName;
    @NonNull
    private Boolean isActive;
    @Length(min = 10, max = 10, message = "Password must be exactly 10 characters long")
    private String password;

    public TraineeStatusToggleRequest(String userName, @NonNull Boolean isActive, String password) {
        this.userName = userName;
        this.isActive = isActive;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TraineeStatusToggleRequest{" +
                "userName='" + userName + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                '}';
    }
}
