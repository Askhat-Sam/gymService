package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


public class TrainerRegistrationResponse {
    @NotBlank
    private String userName;
    @Length(min = 10, max = 10, message = "Password must be exactly 10 characters long")
    private String password;

    private String token;

    public TrainerRegistrationResponse(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public TrainerRegistrationResponse() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TrainerRegistrationResponse{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
