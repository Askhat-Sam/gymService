package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;

public class TrainingWorkloadTotalRequest{
    @NotBlank
    private String trainerUsername;


    public TrainingWorkloadTotalRequest(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public TrainingWorkloadTotalRequest() {
    }

    public String getTrainerUsername() {
        return trainerUsername;
    }

    public void setTrainerUsername(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }


    @Override
    public String toString() {
        return "TrainingWorkloadTotalRequest{" +
                "trainerUsername='" + trainerUsername + '\'' +

                '}';
    }
}
