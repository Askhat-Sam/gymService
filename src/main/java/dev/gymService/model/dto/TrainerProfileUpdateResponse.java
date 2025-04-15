package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;

import java.util.List;


public class TrainerProfileUpdateResponse {
    @NotBlank
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Positive(message = "Must be a positive number")
    private Long specialization;
    @NonNull
    private Boolean isActive;
    @NonNull
    List<TraineeDTO> trainers;

    public TrainerProfileUpdateResponse(String userName, String firstName, String lastName, Long specialization, @NonNull Boolean isActive, @NonNull List<TraineeDTO> trainers) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.trainers = trainers;
    }

    public TrainerProfileUpdateResponse() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Long specialization) {
        this.specialization = specialization;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<TraineeDTO> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<TraineeDTO> trainers) {
        this.trainers = trainers;
    }

    @Override
    public String toString() {
        return "TrainerProfileUpdateResponse{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization=" + specialization +
                ", isActive=" + isActive +
                ", trainers=" + trainers +
                '}';
    }
}
