package dev.gymService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "trainer")
@PrimaryKeyJoinColumn(name = "user_id")
public class Trainer extends User {
    @Column(name = "specialization", nullable = false)
    private Long specialization;
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    private Long userId;
    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    private List<Trainee> trainees;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Training> trainings;

    public Trainer() {
    }

    public Long getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Long specialization) {
        this.specialization = specialization;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Trainee> getTrainees() {
        return trainees;
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
    }

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "specialization=" + specialization +
                ", userId=" + userId +
                '}';
    }
}
