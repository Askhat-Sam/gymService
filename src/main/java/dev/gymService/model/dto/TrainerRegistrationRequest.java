package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerRegistrationRequest extends AbstractRequest{
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Long specialization;
}
