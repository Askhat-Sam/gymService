package dev.gymService.model;

public class Trainer extends User{
    private String specialization;
    private String userId;

    public Trainer(String firstName, String lastName, String userName, String password, Boolean isActive, String specialization, String userId) {
        super(firstName, lastName, userName, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "specialization='" + specialization + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
