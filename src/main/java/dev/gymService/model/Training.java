package dev.gymService.model;

import lombok.*;
import org.hibernate.annotations.Fetch;

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
    @Column(name="id")
    private Long trainingId;
    @Column(name="trainee_id", insertable = false, updatable = false)
    private Long traineeId;
    @Column(name="trainer_id", insertable = false, updatable = false)
    private Long trainerId;
    @Column(name="training_name")
    private String trainingName;
    @Column(name="training_type_id")
    private Long trainingTypeId;
    @Column(name="training_date")
    private LocalDate trainingDate;
    @Column(name="training_duration")
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
                ", trainerId=" + traineeId +
                ", trainerId=" + trainerId +
                ", trainingName='" + trainingName + '\'' +
                ", trainingDate=" + trainingDate +
                ", trainingDuration=" + trainingDuration +
                '}';
    }
}
