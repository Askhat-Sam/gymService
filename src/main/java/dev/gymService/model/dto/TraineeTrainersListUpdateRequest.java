package dev.gymService.model.dto;

import dev.gymService.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeTrainersListUpdateRequest extends AbstractRequest{
    @NonNull
    private String userName;
    @NonNull
    private List<Trainer> trainers;
    @NonNull
    private String password;
}
