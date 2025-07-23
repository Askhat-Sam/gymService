@update_trainee_profile
Feature: Update Trainee Profile

  Scenario: Successfully update an existing trainee profile
    Given a trainee with username "Ivan.Ivanov" exists
    When the client sends a request to update trainee "Ivan.Ivanov" with firstName "Ivan", lastName "Ivanov", dateOfBirth "1990-02-02", address "Furmanova 123", isActive "true" and password "12345678910"
    Then the update trainee response status should be 200
    And the update response should contain "Ivan"