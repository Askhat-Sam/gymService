package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class TraineePasswordChangeRequest extends AbstractRequest {
    @NotBlank
    private String userName;
    @Length(min = 10, max = 10, message = "Password must be exactly 10 characters long")
    private String newPassword;

    public TraineePasswordChangeRequest(String userName, String newPassword) {
        this.userName = userName;
        this.newPassword = newPassword;
    }

    public TraineePasswordChangeRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String toString() {
        return "TraineePasswordChangeRequest{" +
                "userName='" + userName + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
