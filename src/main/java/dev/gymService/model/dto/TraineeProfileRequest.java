package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class TraineeProfileRequest extends AbstractRequest{
    @NotBlank
    private String userName;

    public TraineeProfileRequest(String userName) {
        this.userName = userName;
    }

    public TraineeProfileRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public String toString() {
        return "TraineeProfileRequest{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
