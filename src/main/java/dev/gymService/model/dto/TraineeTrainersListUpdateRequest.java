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

    public TraineeTrainersListUpdateRequest(String userName, @NonNull List<Trainer> trainers) {
        this.userName = userName;
        this.trainers = trainers;
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

    @Override
    public String toString() {
        return "TraineeTrainersListUpdateRequest{" +
                "userName='" + userName + '\'' +
                ", trainers=" + trainers +
                '}';
    }
}
