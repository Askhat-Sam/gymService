package dev.gymService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Table(name = "training_type")
@Data
@NoArgsConstructor
@Immutable
public class TrainingType {
    @Id
    private Long id;
    @Column(name = "training_type_name", nullable = false)
    private String trainingTypeName;
}
