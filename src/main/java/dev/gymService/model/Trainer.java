package dev.gymService.model;

public class Trainer extends User{
    private String specialization;
    private Long userId;

    public Trainer(Long userId, String firstName, String lastName, String userName, String password,
                   Boolean isActive, String specialization) {
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
