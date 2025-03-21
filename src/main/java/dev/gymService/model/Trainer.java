package dev.gymService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
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

    @Override
    public String toString() {
        return "Trainer{" +
                "specialization=" + specialization +
                ", userId=" + userId +
                '}';
    }
}
