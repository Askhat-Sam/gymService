@add_training
Feature: Add new training session

  Scenario: Successfully add a new training session
    Given a valid training add request with trainee "Ivan.Ivanov" and trainer "Vladislav.Bekmeev" and trainingName "Cardio" and trainingDate "2025-01-01" and trainingDuration "60" and actionType "ADD"
    When the client sends a request to add a training to endpoint "/gym-service/trainings/addTraining"
    Then the training add response status should be 200
    And the training add response message should be "Training has been added successfully"

  Scenario: Fail to add training with nonexistent trainee
    Given a invalid training add request with trainee "unknown" and trainer "Vladislav.Bekmeev" and trainingName "Cardio" and trainingDate "2025-01-01" and trainingDuration "60" and actionType "ADD"
    When the client sends a request to add a training to endpoint "/gym-service/trainings/addTraining"
    Then the training add response status should be 500
    And the training add response should contain error message "User [unknown] not found in DB"