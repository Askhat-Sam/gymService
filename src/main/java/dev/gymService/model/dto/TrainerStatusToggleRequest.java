package dev.gymService.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerStatusToggleRequest {
    @NonNull
    private String userName;
    @NonNull
    private Boolean isActive;
    @NonNull
    private String password;
}
