package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerProfileUpdateResponse {
    @NonNull
    private String userName;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Long specialization;
    @NonNull
    private Boolean isActive;
    @NonNull
    List<TraineeDTO> trainers;
}
