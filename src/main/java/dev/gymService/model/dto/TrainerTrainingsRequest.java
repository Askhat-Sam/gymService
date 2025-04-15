package dev.gymService.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


public class TrainerTrainingsRequest extends AbstractRequest{
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @NotBlank
    private String fromDate;
    @NotBlank
    private String toDate;
    @NotBlank
    private String traineeName;
    @Positive(message = "Must be a positive number")
    private Long trainingType;

    public TrainerTrainingsRequest(String userName, String password, String fromDate, String toDate, String traineeName, Long trainingType) {
        this.userName = userName;
        this.password = password;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.traineeName = traineeName;
        this.trainingType = trainingType;
    }

    public TrainerTrainingsRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public Long getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(Long trainingType) {
        this.trainingType = trainingType;
    }

    @Override
    public String toString() {
        return "TrainerTrainingsRequest{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", traineeName='" + traineeName + '\'' +
                ", trainingType=" + trainingType +
                '}';
    }
}
