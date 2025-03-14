package dev.gymService.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trainer")
public class Trainer extends User {
    private Long id;
    @Column(name = "specialization", nullable = false)
    private Long specialization;
    @Column(name = "user_id", nullable = false)
    private Long userId;
    @ManyToMany(mappedBy = "trainers", fetch = FetchType.EAGER)
    private List<Trainee> trainees;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Training> trainings;


    @Override
    public String toString() {
        return "Trainer{" +
                "id=" + id +
                "specialization=" + specialization +
                ", userId=" + userId +
                '}';
    }
}
