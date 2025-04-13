package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

public class TrainerProfileUpdateRequest extends AbstractRequest{
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
    @Length(min = 10, max = 10, message = "Password must be exactly 10 characters long")
    private String password;

    public TrainerProfileUpdateRequest(String userName, String firstName, String lastName, Long specialization, @NonNull Boolean isActive, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.password = password;
    }

    public TrainerProfileUpdateRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TrainerProfileUpdateRequest{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", specialization=" + specialization +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                '}';
    }
}
