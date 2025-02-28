package dev.gymService.model;

import java.time.LocalDate;

public class Trainee extends User {
    private LocalDate dateOfBirth;
    private String address;
    private Long userId;

    public Trainee() {
        super();
    }

    public Trainee(String firstName, String lastName, String userName, String password, Boolean isActive, LocalDate dateOfBirth, String address) {
        super(firstName, lastName, userName, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
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

    @Override
    public String toString() {
        return "Trainee{" +
                "userId='" + userId +
                "', firstName='" + super.getFirstName() +
                "', lastName='" + super.getLastName() +
                "', userName='" + super.getUserName() +
                "', isActive='" + super.getActive() +
                "', dateOfBirth='" + dateOfBirth +
                "', address='" + address + '\'' +
                '}';
    }
}
