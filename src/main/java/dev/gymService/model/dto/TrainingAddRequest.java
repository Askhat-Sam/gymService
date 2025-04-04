package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingAddRequest extends AbstractRequest{
    @NonNull
    private String traineeUserName;
    @NonNull
    private String trainerUsername;
    @NonNull
    private String trainingName;
    @NonNull
    private LocalDate trainingDate;
    @NonNull
    private Long trainingDuration;
    @NonNull
    private String traineePassword;
    @NonNull
    private String trainerPassword;
}
