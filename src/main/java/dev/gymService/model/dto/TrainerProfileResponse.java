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
public class TrainerProfileResponse {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Long specialization;
    @NonNull
    private Boolean isActive;
    @NonNull
    private List<TraineeDTO> trainees;

    public TrainerProfileResponse(String firstName, String lastName, Long specialization, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
    }
}
