package dev.gymService.model;

import java.util.Date;

public class Trainee {
    private Date dateOfBirth;
    private String address;
    private String userId;

    public Trainee(Date dateOfBirth, String address, String userId) {
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
