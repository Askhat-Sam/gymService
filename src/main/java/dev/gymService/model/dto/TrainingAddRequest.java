package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;

import java.time.LocalDate;

public class TrainingAddRequest extends AbstractRequest {
    @NotBlank
    private String traineeUserName;
    @NotBlank
    private String trainerUsername;
    @NotBlank
    private String trainingName;
    @NonNull
    private LocalDate trainingDate;
    @Positive(message = "Must be a positive number")
    private Long trainingDuration;

    public TrainingAddRequest(String traineeUserName, String trainerUsername, String trainingName, @NonNull LocalDate trainingDate, Long trainingDuration) {
        this.traineeUserName = traineeUserName;
        this.trainerUsername = trainerUsername;
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;

    }

    public TrainingAddRequest() {
    }

    public String getTraineeUserName() {
        return traineeUserName;
    }

    public void setTraineeUserName(String traineeUserName) {
        this.traineeUserName = traineeUserName;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public LocalDate getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(LocalDate trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Long getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Long trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    @Override
    public String toString() {
        return "TrainingAddRequest{" +
                "traineeUserName='" + traineeUserName + '\'' +
                ", trainerUsername='" + trainerUsername + '\'' +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}
