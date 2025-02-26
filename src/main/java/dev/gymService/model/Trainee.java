package dev.gymService.model;

import dev.gymService.utills.UserInformationUtility;

import java.time.LocalDate;
import java.util.Date;

public class Trainee extends User {
    private LocalDate dateOfBirth;
    private String address;
    private Long userId;


    public Trainee(Long userId, String firstName, String lastName, String userName, String password, Boolean isActive, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, userName, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
