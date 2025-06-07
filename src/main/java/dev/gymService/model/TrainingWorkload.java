package dev.gymService.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public class TrainingWorkload {
    @NotBlank
    private String trainerUsername;
    @NotBlank
    private String trainerFirstName;
    @NotBlank
    private String trainerLastName;
    @NotBlank
    private boolean trainerStatus;
    @NotNull
    private Map<Integer, Map<Integer, Long>> trainingDuration;

    public TrainingWorkload() {
    }

    public TrainingWorkload(String trainerUsername, String trainerFirstName, String trainerLastName, boolean trainerStatus, Map<Integer, Map<Integer, Long>> trainingDuration) {
        this.trainerUsername = trainerUsername;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
        this.trainerStatus = trainerStatus;
        this.trainingDuration = trainingDuration;
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public String getTrainerFirstName() {
        return trainerFirstName;
    }

    public void setTrainerFirstName(String trainerFirstName) {
        this.trainerFirstName = trainerFirstName;
    }

    public String getTrainerLastName() {
        return trainerLastName;
    }

    public void setTrainerLastName(String trainerLastName) {
        this.trainerLastName = trainerLastName;
    }

    public boolean isTrainerStatus() {
        return trainerStatus;
    }

    public void setTrainerStatus(boolean trainerStatus) {
        this.trainerStatus = trainerStatus;
    }

    public Map<Integer, Map<Integer, Long>> getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Map<Integer, Map<Integer, Long>> trainingDuration) {
        this.trainingDuration = trainingDuration;
    }
    @Override
    public String toString() {
        return "TrainingWorkload{" +
                "trainerUsername='" + trainerUsername + '\'' +
                ", trainerFirstName='" + trainerFirstName + '\'' +
                ", trainerLastName='" + trainerLastName + '\'' +
                ", trainerStatus=" + trainerStatus +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}

