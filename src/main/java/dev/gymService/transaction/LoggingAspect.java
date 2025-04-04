package dev.gymService.transaction;

import dev.gymService.model.dto.AbstractRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    // After successful call of TraineeController methods
    @AfterReturning(pointcut = "execution(* dev.gymService.controller.TraineeController.*(..))", returning = "response")
    public void logRestCallDetailsForTrainee(JoinPoint joinPoint, Object response) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        // Get the arguments (TraineeLoginRequest) from the join point
        Object[] args = joinPoint.getArgs();
        AbstractRequest request = null;

        // Get method name for endpoint information
        String methodName = joinPoint.getSignature().getName();

        // Loop through the arguments until getting the request
        for (Object arg : args) {
            if (arg instanceof AbstractRequest) {
                request = (AbstractRequest) arg;
            }
        }

        if (request != null) {
            // Get transactionId from MDC
            String transactionId = MDC.get("transactionId");

            // Define response status and message
            String status = "200";
            String responseMessage = response != null ? response.toString() : "No Response";

            // If the response is a ResponseEntity, get status code and body
            if (response instanceof ResponseEntity<?> responseEntity) {
                status = String.valueOf(responseEntity.getStatusCodeValue());
                responseEntity.getBody();
                responseMessage = responseEntity.getBody().toString();
            }

            // Log the complete request and response details
            logger.info("Transaction ID: {} | Request method: {} | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                    transactionId, methodName, "/gym-service/trainees/" + methodName, request, status, responseMessage);
        }
    }

    // After an exception is thrown by the TraineeController methods
    @AfterThrowing(pointcut = "execution(* dev.gymService.controller.TraineeController.*(..))", throwing = "exception")
    public void logMethodExceptionForTrainee(JoinPoint joinPoint, Throwable exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        // Get the arguments from the join point
        Object[] args = joinPoint.getArgs();
        AbstractRequest request = null;

        // Get method name for endpoint information
        String methodName = joinPoint.getSignature().getName();

        // Loop through the arguments until getting the request
        for (Object arg : args) {
            if (arg instanceof AbstractRequest) {
                request = (AbstractRequest) arg;
            }
        }

        if (request != null) {
            // Get transactionId from MDC
            String transactionId = MDC.get("transactionId");

            // Log the exception details
            logger.error("Transaction ID: {} | Request method: {} | Endpoint: {} | Request: {} | Exception: {}",
                    transactionId, methodName, "/gym-service/trainees/" + methodName, request, exception.getMessage(), exception);
        }
    }

    // After successful call of TrainerController methods
    @AfterReturning(pointcut = "execution(* dev.gymService.controller.TrainerController.*(..))", returning = "response")
    public void logRestCallDetailsForTrainer(JoinPoint joinPoint, Object response) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        // Get the arguments (TraineeLoginRequest) from the join point
        Object[] args = joinPoint.getArgs();
        AbstractRequest request = null;

        // Get method name for endpoint information
        String methodName = joinPoint.getSignature().getName();

        // Loop through the arguments until getting the request
        for (Object arg : args) {
            if (arg instanceof AbstractRequest) {
                request = (AbstractRequest) arg;
            }
        }

        if (request != null) {
            // Get transactionId from MDC
            String transactionId = MDC.get("transactionId");

            // Define response status and message
            String status = "200";
            String responseMessage = response != null ? response.toString() : "No Response";

            // If the response is a ResponseEntity, get status code and body
            if (response instanceof ResponseEntity<?> responseEntity) {
                status = String.valueOf(responseEntity.getStatusCodeValue());
                responseEntity.getBody();
                responseMessage = responseEntity.getBody().toString();
            }

            // Log the complete request and response details
            logger.info("Transaction ID: {} | Request method: {} | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                    transactionId, methodName, "/gym-service/trainers/" + methodName, request, status, responseMessage);
        }
    }

    // After an exception is thrown by the TrainerController methods
    @AfterThrowing(pointcut = "execution(* dev.gymService.controller.TraineeController.*(..))", throwing = "exception")
    public void logMethodExceptionForTrainer(JoinPoint joinPoint, Throwable exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        // Get the arguments from the join point
        Object[] args = joinPoint.getArgs();
        AbstractRequest request = null;

        // Get method name for endpoint information
        String methodName = joinPoint.getSignature().getName();

        // Loop through the arguments until getting the request
        for (Object arg : args) {
            if (arg instanceof AbstractRequest) {
                request = (AbstractRequest) arg;
            }
        }

        if (request != null) {
            // Get transactionId from MDC
            String transactionId = MDC.get("transactionId");

            // Log the exception details
            logger.error("Transaction ID: {} | Request method: {} | Endpoint: {} | Request: {} | Exception: {}",
                    transactionId, methodName, "/gym-service/trainers/" + methodName, request, exception.getMessage(), exception);
        }
    }

    // After successful call of TraineeController methods
    @AfterReturning(pointcut = "execution(* dev.gymService.controller.TrainingController.*(..))", returning = "response")
    public void logRestCallDetailsForTraining(JoinPoint joinPoint, Object response) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        // Get the arguments (TraineeLoginRequest) from the join point
        Object[] args = joinPoint.getArgs();
        AbstractRequest request = null;

        // Get method name for endpoint information
        String methodName = joinPoint.getSignature().getName();

        // Loop through the arguments until getting the request
        for (Object arg : args) {
            if (arg instanceof AbstractRequest) {
                request = (AbstractRequest) arg;
            }
        }

        if (request != null) {
            // Get transactionId from MDC
            String transactionId = MDC.get("transactionId");

            // Define response status and message
            String status = "200";
            String responseMessage = response != null ? response.toString() : "No Response";

            // If the response is a ResponseEntity, get status code and body
            if (response instanceof ResponseEntity<?> responseEntity) {
                status = String.valueOf(responseEntity.getStatusCodeValue());
                responseEntity.getBody();
                responseMessage = responseEntity.getBody().toString();
            }


            // Log the complete request and response details
            logger.info("Transaction ID: {} | Request method: {} | Endpoint: {} | Request: {} | Response status: {} | Response message: {}",
                    transactionId, methodName, "/gym-service/trainings/" + methodName, request, status, responseMessage);
        }
    }

    // After an exception is thrown by the TrainingController methods
    @AfterThrowing(pointcut = "execution(* dev.gymService.controller.TrainingController.*(..))", throwing = "exception")
    public void logMethodExceptionForTraining(JoinPoint joinPoint, Throwable exception) {
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());

        // Get the arguments from the join point
        Object[] args = joinPoint.getArgs();
        AbstractRequest request = null;

        // Get method name for endpoint information
        String methodName = joinPoint.getSignature().getName();

        // Loop through the arguments until getting the request
        for (Object arg : args) {
            if (arg instanceof AbstractRequest) {
                request = (AbstractRequest) arg;
            }
        }

        if (request != null) {
            // Get transactionId from MDC
            String transactionId = MDC.get("transactionId");

            // Log the exception details
            logger.error("Transaction ID: {} | Request method: {} | Endpoint: {} | Request: {} | Exception: {}",
                    transactionId, methodName, "/gym-service/trainings/" + methodName, request, exception.getMessage(), exception);
        }
    }
}
