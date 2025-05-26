package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


public class TraineeNotAssignedTrainersRequest extends AbstractRequest {
    @NotBlank
    private String userName;

    public TraineeNotAssignedTrainersRequest(String userName) {
        this.userName = userName;
    }

    public TraineeNotAssignedTrainersRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TraineeNotAssignedTrainersRequest{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
