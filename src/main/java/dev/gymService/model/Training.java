package dev.gymService.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="training")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;
    @Column(name="trainer_id")
    private Long trainerId;
    @Column(name="training_name")
    private String trainingName;
    @Column(name="training_type")
    /// ? @embeded how to map the string to onject of tyep TrainingType????????????????????????????????????????????
    private TrainingType trainingType;
    @Column(name="training_date")
    private LocalDate trainingDate;
    @Column(name="training_duration")
    private Long trainingDuration;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Override
    public String toString() {
        return "Training{" +
                "traineeId=" + trainingId +
                ", trainerId=" + trainerId +
                ", trainingName='" + trainingName + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}
