package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;


public class TraineeTrainingsRequest extends AbstractRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String fromDate;
    @NotBlank
    private String toDate;
    @NotBlank
    private String trainerName;
    @Positive(message = "Must be a positive number")
    private Long trainingType;

    public TraineeTrainingsRequest(String userName, String fromDate, String toDate, String trainerName, Long trainingType) {
        this.userName = userName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.trainerName = trainerName;
        this.trainingType = trainingType;
    }

    public TraineeTrainingsRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Long getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(Long trainingType) {
        this.trainingType = trainingType;
    }

    @Override
    public String toString() {
        return "TraineeTrainingsRequest{" +
                "userName='" + userName + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", trainerName='" + trainerName + '\'' +
                ", trainingType=" + trainingType +
                '}';
    }
}
