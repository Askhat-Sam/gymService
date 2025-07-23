@toggle_trainee_status
Feature: Toggle trainee status

  Scenario: Toggle status of an existing trainee
    Given a trainee with username "Ivan.Ivanov" exists
    When the client sends a toggle status request with username "Ivan.Ivanov" to endpoint "/gym-service/trainees/toggleTraineeStatus"
    Then the response status should be 200
    And the toggle status response should contain "User status has been successfully changed"