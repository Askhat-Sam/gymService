package dev.gymService.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class TrainingDTO {
    @NonNull
    private String trainingName;
    @NonNull
    private LocalDate trainingDate;
    @NonNull
    private Long trainingTypeId;
    @NonNull
    private Long trainingDuration;
    @NonNull
    private String trainerName;
}
