@registerTrainee
Feature: Register new trainee

  # First scenario for registering the trainee when valid input is provided
  Scenario: Register trainee with valid input
    Given a new trainee with firstName "Alexandr" and lastName "Egorov" and dateOfBirth "2020-01-01" and address "Mailina 176"
    When the client sends POST request to "/gym-service/trainees/registerNewTrainee"
    Then the trainee registration response status should be 200
    And the response should contain a JWT token

  # Second scenario for registering the trainee when invalid input is provided
  Scenario: Failure to register trainee with invalid input
    Given a new trainee without any parameters
    When the client sends POST request to "/gym-service/trainees/registerNewTrainee"
    Then the trainee registration response status should be 500