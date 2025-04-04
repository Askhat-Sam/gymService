package dev.gymService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String message;
    private int statusCode;
    public ErrorDetails(String message)
    {
        super();
        this.message = message;
    }
}
