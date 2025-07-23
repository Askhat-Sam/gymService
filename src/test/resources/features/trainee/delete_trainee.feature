Feature: Delete a trainee

  Scenario: Successfully delete a trainee if exists
    Given a trainee with username "Denis.Denisov" exists
    When the client sends a delete trainee request for username "Denis.Denisov" to endpoint "/gym-service/trainees/deleteTrainee"
    Then the delete response status should be 200
    And the delete response should contain "User has been successfully deleted"

  Scenario: Failure  to delete a trainee if exists
    Given a trainee with username "Denis.Denisov777" does not exist in DB
    When the client sends a delete trainee request for username "Denis.Denisov777" to endpoint "/gym-service/trainees/deleteTrainee"
    Then the delete response status should be 500