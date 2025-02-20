package dev.gymService.model;

import java.util.Date;

public class Trainee extends User {
    private Date dateOfBirth;
    private String address;
    private String userId;


    public Trainee(String firstName, String lastName, String userName, String password, Boolean isActive, Date dateOfBirth, String address, String userId) {
        super(firstName, lastName, userName, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
