package dev.gymService.model.dto;

import lombok.NonNull;

import java.util.List;

public class TraineeTrainersListUpdateResponse {
    @NonNull
    private List<TrainerDTO> trainers;

    public TraineeTrainersListUpdateResponse(@NonNull List<TrainerDTO> trainers) {
        this.trainers = trainers;
    }

    public TraineeTrainersListUpdateResponse() {
    }

    public List<TrainerDTO> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<TrainerDTO> trainers) {
        this.trainers = trainers;
    }

    @Override
    public String toString() {
        return "TraineeTrainersListUpdateResponse{" +
                "trainers=" + trainers +
                '}';
    }
}
