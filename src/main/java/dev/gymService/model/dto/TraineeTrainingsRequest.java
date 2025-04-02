package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTrainingsRequest extends AbstractRequest{
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private String fromDate;
    @NonNull
    private String toDate;
    @NonNull
    private String trainerName;
    @NonNull
    private Long trainingType;
}
