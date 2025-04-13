package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;

import java.time.LocalDate;

public class TrainingDTO {
    @NotBlank
    private String trainingName;
    @NonNull
    private LocalDate trainingDate;
    @Positive(message = "Must be a positive number")
    private Long trainingTypeId;
    @Positive(message = "Must be a positive number")
    private Long trainingDuration;
    @NotBlank
    private String trainerName;

    public TrainingDTO(String trainingName, @NonNull LocalDate trainingDate, Long trainingTypeId, Long trainingDuration, String trainerName) {
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingTypeId = trainingTypeId;
        this.trainingDuration = trainingDuration;
        this.trainerName = trainerName;
    }

    public TrainingDTO() {
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

    public Long getTrainingTypeId() {
        return trainingTypeId;
    }

    public void setTrainingTypeId(Long trainingTypeId) {
        this.trainingTypeId = trainingTypeId;
    }

    public Long getTrainingDuration() {
        return trainingDuration;
    }

    public void setTrainingDuration(Long trainingDuration) {
        this.trainingDuration = trainingDuration;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    @Override
    public String toString() {
        return "TrainingDTO{" +
                "trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingTypeId=" + trainingTypeId +
                ", trainingDuration=" + trainingDuration +
                ", trainerName='" + trainerName + '\'' +
                '}';
    }
}
