package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class TraineeProfileUpdateRequest extends AbstractRequest{
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
    @Length(min = 10, max = 10, message = "Password must be exactly 10 characters long")
    private String password;

    public TraineeProfileUpdateRequest(String userName, String firstName, String lastName, @NonNull LocalDate dateOfBirth, String address, @NonNull Boolean isActive, String password) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
        this.password = password;
    }

    public TraineeProfileUpdateRequest() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TraineeProfileUpdateRequest{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                '}';
    }
}
