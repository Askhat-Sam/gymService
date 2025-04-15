package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class TrainerProfileRequest extends AbstractRequest{
    @NotBlank
    private String userName;
    @Length(min = 10, max = 10, message = "Password must be exactly 10 characters long")
    private String password;

    public TrainerProfileRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public TrainerProfileRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TrainerProfileRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
