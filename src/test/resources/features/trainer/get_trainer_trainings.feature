@get_trainer_trainings
Feature: Get Trainer Trainings

  Scenario: Retrieve all trainings for a trainer
    Given a trainer with username "Vladislav.Bekmeev" exists
    When the trainer sends a request to get trainings for username "Vladislav.Bekmeev" from date "2020-01-01" to date "2026-01-01" with trainee "Ivan.Ivanov" and training type "1" to endpoint "/gym-service/trainers/getTrainerTrainings"
    Then the trainer training response status should be 200
    And the trainer training response should contain a list of trainings