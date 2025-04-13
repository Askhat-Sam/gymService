package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;


public class TraineeProfileUpdateResponse {
    @NotBlank
    private String userName;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NonNull
    private LocalDate dateOfBirth;
    @NotBlank
    private String address;
    @NonNull
    private Boolean isActive;
    @NonNull
    List<TrainerDTO> trainers;

    public TraineeProfileUpdateResponse(String userName, String firstName, String lastName, @NonNull LocalDate dateOfBirth, String address, @NonNull Boolean isActive, @NonNull List<TrainerDTO> trainers) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
        this.trainers = trainers;
    }

    public TraineeProfileUpdateResponse() {
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public List<TrainerDTO> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<TrainerDTO> trainers) {
        this.trainers = trainers;
    }

    @Override
    public String toString() {
        return "TraineeProfileUpdateResponse{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", trainers=" + trainers +
                '}';
    }
}
