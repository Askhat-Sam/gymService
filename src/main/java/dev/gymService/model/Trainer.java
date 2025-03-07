package dev.gymService.model;

public class Trainer extends User {
    private TrainingType specialization;
    private Long userId;

    public Trainer(Long userId, String firstName, String lastName, String userName, String password,
                   Boolean isActive, TrainingType specialization) {
        super(firstName, lastName, userName, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }

    public Trainer() {
        super();
    }

    public TrainingType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(TrainingType specialization) {
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
                ", userId='" + userId + '\'' +
                ", userName='" + super.getUserName() + '\'' +
                ", password='" + super.getPassword() + '\'' +
                '}';
    }
}
