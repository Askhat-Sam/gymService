@update_trainee_trainers
Feature: Update Trainee Trainers

  Scenario: Successfully update trainee's trainers
    Given a trainee with username "Ivan.Ivanov" exists
    And a the trainee with username "Sergey.Sergeyev" exists
    When the client sends a request to update trainee trainers for username "Ivan.Ivanov" to add trainer with username "Sergey.Sergeyev"
    Then the update trainee trainers response status should be 200
    And the update trainee trainers response should contain trainer with username "Sergey.Sergeyev"