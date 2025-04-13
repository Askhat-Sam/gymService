package dev.gymService.exception;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private final Logger logger = Logger.getLogger("ExceptionControllerAdvice");

    // User failed to authenticate
    @ExceptionHandler(value = UserNotAuthenticatedException.class)
    public ResponseEntity<ErrorDetails> userNotAuthenticatedHandler(UserNotAuthenticatedException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.BAD_REQUEST.value());
        ResponseEntity<ErrorDetails> responseEntity = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDetails);
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + "No body is available" + " Response: " + responseEntity);

        return responseEntity;
    }

    // User not found in DB
    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<ErrorDetails> userNotFoundHandler(NoSuchElementException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseEntity<ErrorDetails> responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + "No body is available" + " Response: " + responseEntity);
        return responseEntity;
    }

    // Incorrect URI request in POSTMAN
    @ExceptionHandler(value = NoResourceFoundException.class)
    public ResponseEntity<ErrorDetails> noResourceFoundExceptionHandler(NoResourceFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseEntity<ErrorDetails> responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + "No body is available" + " Response: " + responseEntity);
        return responseEntity;
    }

    // Request took too long time
    @ExceptionHandler(value = TimeoutException.class)
    public ResponseEntity<ErrorDetails> timeoutExceptionHandler(TimeoutException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.REQUEST_TIMEOUT.value());
        ResponseEntity<ErrorDetails> responseEntity =ResponseEntity
                .status(HttpStatus.REQUEST_TIMEOUT)
                .body(errorDetails);
                logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + "No body is available" + " Response: " + responseEntity);
        return responseEntity;
    }

    // Value annotated as "Not null" value is null
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDetails> timeoutExceptionHandler(HttpMessageNotReadableException ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.BAD_REQUEST.value());

        ResponseEntity<ErrorDetails> responseEntity = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDetails);

        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + "No body is available" + " Response: " + responseEntity);
        return responseEntity;
    }

    // To handle rest of the exceptions
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDetails> handleNotHandledException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        ResponseEntity<ErrorDetails> responseEntity = ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
        logger.log(Level.INFO, MDC.get("transactionId") + " Request method: " + "No body is available" + " Response: " + responseEntity);
        return responseEntity;
    }
}
