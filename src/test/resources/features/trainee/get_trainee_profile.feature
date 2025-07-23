@get_trainee_profile
Feature: Get trainee profile
  # First scenario for getting trainee profile by username
  Scenario: Successfully get trainee profile existing in DB  by username
    Given a trainee with username "Ivan.Ivanov" exists
    When the client sends a request to get the profile for username "Ivan.Ivanov" to endpoint "/gym-service/trainees/getTraineeProfile"
    Then the trainee profile response status should be 200
    And the response should contain trainee first name "Ivan"

  # First scenario for getting trainee profile by username
  Scenario: Failure to get trainee profile by username since user not exist in DB
    Given a trainee with username "Ivan.Ivanov777" does not exist in DB
    When the client sends a request to get the profile for username "Ivan.Ivanov777" to endpoint "/gym-service/trainees/getTraineeProfile"
    Then the trainee profile response status should be 500
