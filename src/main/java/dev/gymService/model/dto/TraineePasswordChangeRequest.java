package dev.gymService.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class TraineePasswordChangeRequest extends AbstractRequest{
    @NonNull
    private String userName;
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;
}
