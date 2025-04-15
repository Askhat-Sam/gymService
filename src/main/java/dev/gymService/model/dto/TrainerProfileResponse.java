package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;

import java.util.List;


public class TrainerProfileResponse {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Positive(message = "Must be a positive number")
    private Long specialization;
    @NonNull
    private Boolean isActive;
    @NonNull
    private List<TraineeDTO> trainees;

    public TrainerProfileResponse(String firstName, String lastName, Long specialization, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
    }

    public TrainerProfileResponse() {
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

    public List<TraineeDTO> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<TraineeDTO> trainees) {
        this.trainees = trainees;
    }

    @Override
    public String toString() {
        return "TrainerProfileResponse{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization=" + specialization +
                ", isActive=" + isActive +
                ", trainees=" + trainees +
                '}';
    }
}
