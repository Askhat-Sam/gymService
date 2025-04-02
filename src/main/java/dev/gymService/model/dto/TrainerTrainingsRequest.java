package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerTrainingsRequest extends AbstractRequest{
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private String fromDate;
    @NonNull
    private String toDate;
    @NonNull
    private String traineeName;
    @NonNull
    private Long trainingType;
}
