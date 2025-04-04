package dev.gymService.model.dto;

import dev.gymService.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class TraineeProfileResponse {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private LocalDate dateOfBirth;
    @NonNull
    private String address;
    @NonNull
    private Boolean isActive;
    @NonNull
    private List<TrainerDTO> trainers;

    public TraineeProfileResponse(String firstName, String lastName, LocalDate dateOfBirth, String address, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
    }
}
