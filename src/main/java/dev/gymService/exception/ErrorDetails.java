package dev.gymService.exception;

public class ErrorDetails {
    private String message;
    private int statusCode;
    public ErrorDetails(String message)
    {
        super();
        this.message = message;
    }

    public ErrorDetails(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public ErrorDetails() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
