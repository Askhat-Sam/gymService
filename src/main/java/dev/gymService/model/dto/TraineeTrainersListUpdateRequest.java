package dev.gymService.model.dto;

import dev.gymService.model.Trainer;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public class TraineeTrainersListUpdateRequest extends AbstractRequest{
    @NotBlank
    private String userName;
    @NonNull
    private List<Trainer> trainers;
    @Length(min = 10, max = 10, message = "Password must be exactly 10 characters long")
    private String password;

    public TraineeTrainersListUpdateRequest(String userName, @NonNull List<Trainer> trainers, String password) {
        this.userName = userName;
        this.trainers = trainers;
        this.password = password;
    }

    public TraineeTrainersListUpdateRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TraineeTrainersListUpdateRequest{" +
                "userName='" + userName + '\'' +
                ", trainers=" + trainers +
                ", password='" + password + '\'' +
                '}';
    }
}
