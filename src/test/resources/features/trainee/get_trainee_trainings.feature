@get_trainee_trainings
Feature: Get Trainee Trainings

  Scenario: Retrieve all trainings for a trainee
    Given a trainee with username "Ivan.Ivanov" exists
    When the trainee sends a request to get trainings for trainee "Ivan.Ivanov" from date "2020-01-01" to date "2026-01-01" with trainer "Vladislav.Bekmeev" and training type "1" to endpoint "/gym-service/trainees/getTraineeTrainings"
    Then the trainee training response status should be 200
    And the response should contain a list of 2 trainings