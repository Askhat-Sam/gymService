package dev.gymService.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotAuthenticatedException extends RuntimeException{
    private String message;
    public UserNotAuthenticatedException(String message) {
        super(message);
        this.message = message;
    }
}
