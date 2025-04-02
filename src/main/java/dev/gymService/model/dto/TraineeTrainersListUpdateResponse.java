package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTrainersListUpdateResponse {
    @NonNull
    private List<TrainerDTO> trainers;
}
