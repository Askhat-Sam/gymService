package dev.gymService.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "training")
public class Training {
    @Id
    @Column(name = "id", nullable = false)
    private Long trainingId;
    @Column(name = "trainee_id", insertable = false, updatable = false)
    private Long traineeId;
    @Column(name = "trainer_id", insertable = false, updatable = false)
    private Long trainerId;
    @Column(name = "training_name", nullable = false)
    private String trainingName;
    @Column(name = "training_type_id", nullable = false)
    private Long trainingTypeId;
    @Column(name = "training_date", nullable = false)
    private LocalDate trainingDate;
    @Column(name = "training_duration", nullable = false)
    private Long trainingDuration;

    @ManyToOne
    @JoinColumn(name = "trainee_id", nullable = false)
    private Trainee trainee;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Override
    public String toString() {
        return "Training{" +
                "trainingId=" + trainingId +
                ", traineeId=" + traineeId +
                ", trainerId=" + trainerId +
                ", trainingName='" + trainingName + '\'' +
                ", trainingTypeId=" + trainingTypeId +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                ", trainee=" + trainee +
                ", trainer=" + trainer +
                '}';
    }
}
